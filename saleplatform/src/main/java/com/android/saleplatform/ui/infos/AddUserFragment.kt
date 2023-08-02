package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import com.android.arch.data.room.dao.UserDao
import com.android.arch.data.room.entity.UserEntity
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.R
import com.android.saleplatform.beans.AddressInfo
import com.android.saleplatform.beans.GoodsInfo
import com.android.saleplatform.beans.UserInfo
import com.android.saleplatform.databinding.FragmentAddUserBinding
import com.android.saleplatform.mapper.Converters
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.ui.BaseFragment
import com.android.saleplatform.ui.adapter.AddressArrayAdapter
import com.android.saleplatform.ui.adapter.MyArrayAdapter1
import com.android.saleplatform.ui.view.CustomSpinner
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddUserFragment : BaseFragment() {

    private var _binding: FragmentAddUserBinding? = null
    private val userModelDataMapper: UserModelDataMapper = UserModelDataMapper()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var goodsAdapter: MyArrayAdapter1? = null
    private val goodsInfoList: MutableList<GoodsInfo> = ArrayList()

    @Volatile
    private var isOperaGoodsSpinnerView = 0 //0:init;1:open;2close;

    @Volatile
    private var isOperaAddressSpinnerView = 0 //0:init;1:open;2close;
    private val addressList: MutableList<AddressInfo> = ArrayList()
    private var addressAdapter: AddressArrayAdapter? = null
    private var goodsInfo: GoodsInfo? = null
    private var addressInfo: AddressInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.btnBack.setOnClickListener {
            back()
        }

        binding.btnModifyStatus.setOnClickListener {
            if (addUser()) {
                sharedViewModel.isAddOrModifyUser.value = true
                back()
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        initData()
        return root
    }

    fun initData() {
        (requireActivity().application as AndroidApplication).applicationComponent.addressDaoRepository()
            .getAddressByName("")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                if (t1 != null && !t1.isEmpty()) {
                    initAddressSpinnerView(
                        userModelDataMapper.AddressEntities2AddressList(
                            t1,
                            null
                        )
                    )
                }
                Log.e(AddUserFragment::class.simpleName, "fff " + t2)
            }
        (requireActivity().application as AndroidApplication).applicationComponent.goodsDaoRepository()
            .getGoods()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                if (t1 != null && !t1.isEmpty()) {
                    initSpinnerView(userModelDataMapper.GoodsEntities2GoodList(t1))
                }
                Log.e(AddUserFragment::class.simpleName, "eee " + t2)
            }
    }

    fun addUser(): Boolean {
        val name: String = binding.textName.text.toString()
        val roomName: String = binding.textRoomName.text.toString()
        val count: String = binding.textCount.text.toString()
        val desc: String = binding.textDesc.text.toString()
        val payed = if (binding.goodStatusPayed.isChecked) 1 else 0
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(roomName) && !TextUtils.isEmpty(count) && goodsInfo != null && addressInfo != null) {
            val userDao =
                (requireActivity().application as AndroidApplication).applicationComponent.userDaoRepository()
            Single.create<Long> {
                val list = userDao.queryUserByAddressId(
                    name,
                    addressInfo?.addressId!!,
                    goodsInfo?.goodsId!!
                )
                if (list != null && !list.isEmpty()) {
                    showToastMessage("已经添加，无需再次添加")
                } else {
                    var id = userDao.insertUser(
                        userModelDataMapper
                            .UserInfo2Entity(
                                UserInfo(
                                    0,
                                    goodsInfo?.goodsId,
                                    addressInfo?.addressId,
                                    goodsInfo,
                                    addressInfo,
                                    name,
                                    roomName,
                                    count.toInt(),
                                    payed,
                                    0,
                                    0,
                                    Converters.dateToTimestamp(null),
                                    Converters.dateToTimestamp(null),
                                    count,
                                    desc
                                )
                            )
                    )
                    Log.e("wuli", "7777777777::" + id)
                    sharedViewModel.isAddOrModifyUser.postValue(true)
                }
                it.onSuccess(1)
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe { t1, t2 ->

                    if (t2 == null) {
                    } else {
                        Log.e("wuli", "****" + t2)
                    }
                }

            return true;
        }
        return false
    }

    private fun initSpinnerView(list: List<GoodsInfo>) {
        goodsInfoList.clear()
        goodsInfoList.addAll(list)
        if (goodsAdapter == null) {
            isOperaGoodsSpinnerView = 0
            goodsInfo = null
            goodsAdapter =
                MyArrayAdapter1(requireContext(), R.layout.spinner_drop_down, goodsInfoList)
            goodsAdapter!!.setDropDownViewResource(com.chad.library.R.layout.support_simple_spinner_dropdown_item)
            binding!!.spinnerGood.setAdapter(goodsAdapter)
            binding!!.spinnerGood.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isOperaGoodsSpinnerView == 2 || goodsInfo == null) {
                        goodsInfo = goodsInfoList.get(position)
                    }

                    // 一定要设置父视图可见，否则 在选择后，Spinner会消失
                    parent?.visibility = View.VISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            binding!!.spinnerGood.setSpinnerEventsListener(object :
                CustomSpinner.OnSpinnerEventsListener {
                override fun onSpinnerOpened() {
                    isOperaGoodsSpinnerView = 1
                }

                override fun onSpinnerClosed() {
                    isOperaGoodsSpinnerView = 2
//                    initData()
                }
            })
        }

    }

    private fun initAddressSpinnerView(list: List<AddressInfo>) {
        addressList.clear()
        addressList.addAll(list)
        if (addressAdapter == null) {
            isOperaAddressSpinnerView = 0
            addressInfo = null
            addressAdapter =
                AddressArrayAdapter(requireContext(), R.layout.spinner_drop_down, addressList)
            addressAdapter!!.setDropDownViewResource(com.chad.library.R.layout.support_simple_spinner_dropdown_item)
            binding!!.spinnerAddress.setAdapter(addressAdapter)
            binding!!.spinnerAddress.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isOperaAddressSpinnerView == 2 || addressInfo == null) {
                        addressInfo = addressList.get(position)
                    }

                    // 一定要设置父视图可见，否则 在选择后，Spinner会消失
                    parent?.visibility = View.VISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            binding!!.spinnerAddress.setSpinnerEventsListener(object :
                CustomSpinner.OnSpinnerEventsListener {
                override fun onSpinnerOpened() {
                    isOperaAddressSpinnerView = 1
                }

                override fun onSpinnerClosed() {
                    isOperaAddressSpinnerView = 2
//                    initData()
                }
            })
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        addressAdapter = null
        goodsAdapter = null
    }
}