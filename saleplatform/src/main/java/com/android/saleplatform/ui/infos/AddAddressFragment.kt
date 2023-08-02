package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.beans.AddressInfo
import com.android.saleplatform.databinding.FragmentAddAddressBinding
import com.android.saleplatform.mapper.Converters
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.presenter.OrderPresenter
import com.android.saleplatform.ui.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class AddAddressFragment : BaseFragment() {
    private val userModelDataMapper: UserModelDataMapper = UserModelDataMapper()

    @Inject
    public lateinit var orderPresenter: OrderPresenter
    private var _binding: FragmentAddAddressBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentAddAddressBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnModifyStatus.setOnClickListener {
            if (addAddress()) {
                sharedViewModel.isAddOrModifiedAddress.value = true
                back()
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        return root
    }

    fun addAddress(): Boolean {
        val address = binding.textGoodName.text.toString()
        if (!TextUtils.isEmpty(address)) {
            val goodsInfo = sharedViewModel.currentMaintainAddedGoodsInfo.value
            var addressDao =
                (requireActivity().application as AndroidApplication).applicationComponent
                    .addressDaoRepository()
            addressDao.getAddressByName(address).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, t2 ->
                    if (t1 != null && !t1.isEmpty()) {
//                        showToastMessage("已经添加地址")
                    } else {
                        var id = addressDao.insertAddress(
                            userModelDataMapper
                                .AddressInfo2AddressEntity(
                                    AddressInfo(
                                        0,
                                        address,
                                        goodsInfo,
                                        Converters.dateToTimestamp(null),
                                        Converters.dateToTimestamp(null)
                                    )
                                )
                        )
                        sharedViewModel.isAddOrModifiedAddress.postValue(true)
                        Log.e("wuli", " " + id)
                    }
                    Log.e("wuli", " " + t2)

                }


            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}