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

class ModifyUserFragment : BaseFragment() {

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
        binding.btnModifyStatus.text = "修改"
        binding.btnModifyStatus.setOnClickListener {
            if (addUser()) {
                sharedViewModel.isAddOrModifyUser.value = true
                back()
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        sharedViewModel.currentModifyUserInfo.observeSticky(viewLifecycleOwner) {
            binding.textName.setText(it.userName)
            binding.textRoomName.setText(it.roomAddress)
            binding.textCount.setText(it.totalMoney)
            binding.textDesc.setText(it.desc)
            binding.goodStatusPayed.isChecked = it.payStatus == 1
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
                Log.e(ModifyUserFragment::class.simpleName, "fff " + t2)
            }
        (requireActivity().application as AndroidApplication).applicationComponent.goodsDaoRepository()
            .getGoods()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                if (t1 != null && !t1.isEmpty()) {
                    initSpinnerView(userModelDataMapper.GoodsEntities2GoodList(t1))
                }
                Log.e(ModifyUserFragment::class.simpleName, "eee " + t2)
            }
    }

    fun addUser(): Boolean {
        val name: String = binding.textName.text.toString()
        val roomName: String = binding.textRoomName.text.toString()
        val count: String = binding.textCount.text.toString()
        val desc: String = binding.textDesc.text.toString()
        val payed = if (binding.goodStatusPayed.isChecked) 1 else 0
        val goodsInfo = sharedViewModel.currentMaintainAddedGoodsInfo.value
        val addressInfo = sharedViewModel.currentMaintainAddedAddressInfo.value
        val userInfo = sharedViewModel.currentModifyUserInfo.value
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(roomName) && !TextUtils.isEmpty(count)) {
            val userDao =
                (requireActivity().application as AndroidApplication).applicationComponent.userDaoRepository()
            Single.create<Long> {
                userDao.updateUser(
                    userModelDataMapper
                        .UserInfo2Entity(
                            UserInfo(
                                userInfo?.id,
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
                                userInfo?.createDate,
                                Converters.dateToTimestamp(null),
                                count,
                                desc
                            )
                        )
                )
                Log.e("wuli", "7777777777::" + id)
                sharedViewModel.isAddOrModifyUser.postValue(true)
                it.onSuccess(1)
            }.subscribeOn(Schedulers.io()).subscribe { t1, t2 ->
                Log.e("wuli", "****" + t2)
            }

            return true;
        }
        return false
    }

    private fun initSpinnerView(list: List<GoodsInfo>) {
        goodsInfoList.clear()
        goodsInfoList.addAll(list)
        val userInfo = sharedViewModel.currentModifyUserInfo.value
        if (goodsAdapter == null) {
            isOperaGoodsSpinnerView = 0
            list.forEachIndexed { index, addressInfo ->
                if (addressInfo.goodsId == userInfo?.goodsId) {
                    binding!!.spinnerGood.setSelection(index)
                }
            }
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
                    if (isOperaGoodsSpinnerView == 2 || sharedViewModel.currentMaintainAddedGoodsInfo.value == null) {

                        sharedViewModel.currentMaintainAddedGoodsInfo.postValue(goodsInfoList[position])
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

        val userInfo = sharedViewModel.currentModifyUserInfo.value
        if (addressAdapter == null) {
            isOperaAddressSpinnerView = 0
            list.forEachIndexed { index, addressInfo ->
                if (addressInfo.addressId == userInfo?.addressId) {
                    binding!!.spinnerAddress.setSelection(index)
                }
            }
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
                    if (isOperaAddressSpinnerView == 2 || sharedViewModel.currentMaintainAddedAddressInfo.value == null) {

                        sharedViewModel.currentMaintainAddedAddressInfo.postValue(addressList[position])
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
    }
}