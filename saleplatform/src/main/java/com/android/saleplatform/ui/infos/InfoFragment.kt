package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.saleplatform.R
import com.android.saleplatform.beans.AddressWithUserInfos
import com.android.saleplatform.beans.GoodsInfo
import com.android.saleplatform.databinding.FragmentInfosBinding
import com.android.saleplatform.internal.di.components.UserComponent
import com.android.saleplatform.presenter.OrderInfoMaintainPresenter
import com.android.saleplatform.ui.BaseFragment
import com.android.saleplatform.ui.adapter.GoodsAdapter
import com.android.saleplatform.ui.adapter.MyArrayAdapter1
import com.android.saleplatform.ui.view.CustomSpinner.OnSpinnerEventsListener
import javax.inject.Inject

class InfoFragment : BaseFragment() {

    @Inject
    public lateinit var infoMaintainPresenter: OrderInfoMaintainPresenter
    private var binding: FragmentInfosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    @Inject
    lateinit var goodsAdapter: GoodsAdapter

    private val goodsInfoList: MutableList<GoodsInfo> = ArrayList()
    private var adapter: MyArrayAdapter1? = null
    private var homeViewModel: InfoViewModel? = null
    private var rootView: View? = null

    @Volatile
    private var isOperaSpinnerView = 0 //0:init;1:open;2close;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(UserComponent::class.java).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (rootView == null) {
            binding = FragmentInfosBinding.inflate(inflater, container, false)
            rootView = binding!!.getRoot()
            homeViewModel = ViewModelProvider(this).get(InfoViewModel::class.java)
            val textView: TextView = binding!!.textNotifications
            sharedViewModel.currentMaintainAddedGoodsInfo.observeSticky(
                viewLifecycleOwner
            ) { goodsInfo: GoodsInfo ->
                Log.e("wuli", "onCreateView111")
                textView.text = goodsInfo.goodsName
                infoMaintainPresenter.getUserListGroupByAddrByGoodId(goodsInfo)
            }
            binding!!.btnAddGoods.setOnClickListener {
                replaceChildrenFragment(AddGoodsFragment(), R.id.ly_container)
            }
            binding!!.btnAddress.setOnClickListener {
                val goodsInfo = sharedViewModel.currentMaintainAddedGoodsInfo.value
                if (goodsInfo != null) {
                    replaceChildrenFragment(AddUserFragment(), R.id.ly_container)
                } else {
                    showToastMessage("请先创建商品")
                }
            }
            infoMaintainPresenter.setViewModel(homeViewModel)

        }

        initObserve(homeViewModel)
        initData()
        return rootView
    }

    private fun initObserve(viewModel: InfoViewModel?) {
        viewModel!!.addressInfoWithUsersList.observe(
            this.viewLifecycleOwner
        ) { addressWithUserInfos: List<AddressWithUserInfos> ->
            initView(
                addressWithUserInfos
            )
        }
        viewModel.goodsInfos.observe(
            this.viewLifecycleOwner
        ) { goodsInfoList: List<GoodsInfo> ->
            initSpinnerView(
                goodsInfoList
            )
        }
        sharedViewModel.isAddOrModifiedGoods.observeSticky(viewLifecycleOwner) {
            if (it) {
                infoMaintainPresenter.getGoodList()
            }
        }
        sharedViewModel.isAddOrModifiedAddress.observeSticky(viewLifecycleOwner) {
            if (it) {
                Log.e("wuli", "******************")
                infoMaintainPresenter.getUserListGroupByAddrByGoodId(sharedViewModel.currentMaintainAddedGoodsInfo.value)
            }
        }
        sharedViewModel.isAddOrModifyUser.observeSticky(viewLifecycleOwner) {
            if (it) {
                Log.e("wuli", "******************")
                infoMaintainPresenter.getUserListGroupByAddrByGoodId(sharedViewModel.currentMaintainAddedGoodsInfo.value)
            }
        }
    }

    private fun initData() {
        infoMaintainPresenter.getGoodList()
    }

    private fun initSpinnerView(list: List<GoodsInfo>) {
        goodsInfoList.clear()
        goodsInfoList.addAll(list)
        if (adapter == null) {
            isOperaSpinnerView = 0

            adapter = MyArrayAdapter1(requireContext(), R.layout.spinner_drop_down, goodsInfoList)
            adapter!!.setDropDownViewResource(com.chad.library.R.layout.support_simple_spinner_dropdown_item)
            binding!!.spinner1.setAdapter(adapter)
            binding!!.spinner1.setOnItemSelectedListener(object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (isOperaSpinnerView == 2 || sharedViewModel.currentMaintainAddedGoodsInfo.value == null) {
                        if (goodsAdapter != null) {
                            goodsAdapter.data.clear()
                            goodsAdapter.notifyDataSetChanged()
                        }
                        sharedViewModel.currentMaintainAddedGoodsInfo.postValue(goodsInfoList[position])
                    }

                    // 一定要设置父视图可见，否则 在选择后，Spinner会消失
                    parent?.visibility = View.VISIBLE
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            })
            binding!!.spinner1.setSpinnerEventsListener(object : OnSpinnerEventsListener {
                override fun onSpinnerOpened() {
                    isOperaSpinnerView = 1
                }

                override fun onSpinnerClosed() {
                    isOperaSpinnerView = 2
//                    initData()
                }
            })
        }

    }

    private fun initView(addrWithUsersList: List<AddressWithUserInfos>) {
        goodsAdapter!!.setList(addrWithUsersList)
        binding!!.goodsList.setAdapter(goodsAdapter)
        binding!!.goodsList.setLayoutManager(LinearLayoutManager(requireContext()))
        goodsAdapter!!.setOnItemClickListener { adapter, view, position ->
            val addressWithUserInfos = adapter.getItem(position) as AddressWithUserInfos
            sharedViewModel.currentMaintainAddedAddressInfo.setValue(addressWithUserInfos.addressInfo)
            sharedViewModel.currentMaintainAddressWithUserInfosInfo.setValue(addressWithUserInfos)
            replaceChildrenFragment(AddUserInfoFragment(), R.id.ly_container)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null;
    }
}