package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.beans.GoodsInfo
import com.android.saleplatform.databinding.FragmentAddGoodsBinding
import com.android.saleplatform.mapper.Converters
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.ui.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddGoodsFragment : BaseFragment() {
    private val userModelDataMapper: UserModelDataMapper = UserModelDataMapper()
    private var _binding: FragmentAddGoodsBinding? = null

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

        _binding = FragmentAddGoodsBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnBack.setOnClickListener {
            back()
        }
        binding.btnModifyStatus.setOnClickListener {
            if (addGoods()) {
                back()
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        return root
    }

    fun addGoods(): Boolean {
        val name: String = binding.textGoodName.text.toString()
        val price: String = binding.textGoodPrice.text.toString()
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
            val goodDao = (requireActivity().application as AndroidApplication).applicationComponent
                .goodsDaoRepository()

            goodDao.getGoodsByName(name).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, t2 ->
                    if (t1 != null && !t1.isEmpty()) {
//                        showToastMessage("已经添加地址")
                    } else {
                        goodDao.insertGoods(
                            userModelDataMapper
                                .GoodInfo2GoodEntity(
                                    GoodsInfo(
                                        name, price.toDouble(), 0,
                                        Converters.dateToTimestamp(null),
                                        Converters.dateToTimestamp(null)
                                    )
                                )
                        )
                        sharedViewModel.isAddOrModifiedGoods.postValue(true)
                        Log.e("wuli", "AddGoodsFragment " + id)
                    }
                    Log.e("wuli", " " + t2)
                }

            return true;
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}