package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.R
import com.android.saleplatform.beans.GoodsInfo
import com.android.saleplatform.databinding.FragmentGoodsListBinding
import com.android.saleplatform.internal.di.components.UserComponent
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.ui.BaseFragment
import com.android.saleplatform.ui.adapter.GoodsAdapter1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoodsListFragment : BaseFragment() {
    private val userModelDataMapper: UserModelDataMapper = UserModelDataMapper()
    private var _binding: FragmentGoodsListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var addressAdapter: GoodsAdapter1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getComponent(UserComponent::class.java).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        _binding = FragmentGoodsListBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.btnBack.setOnClickListener {
            back()
        }
        binding.btnModifyStatus.setOnClickListener {
            replaceChildrenFragment(AddGoodsFragment(), R.id.ly_container)
        }
        initObservable()
        initData()
        return root
    }

    fun initData() {

        (requireActivity().application as AndroidApplication).applicationComponent.goodsDaoRepository()
            .getGoods()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                if (t1 != null && !t1.isEmpty()) {
                    initRecList(userModelDataMapper.GoodsEntities2GoodList(t1))
                }
                Log.e(AddUserFragment::class.simpleName, " " + t2)
            }
    }

    fun initObservable() {
        sharedViewModel.isAddOrModifyUser.observeSticky(viewLifecycleOwner) {
            if (it) {
                initData()
            }
        }
        sharedViewModel.isAddOrModifiedGoods.observeSticky(viewLifecycleOwner) {
            if (it) {
                initData()
            }
        }

    }

    fun initRecList(list: List<GoodsInfo>) {
        addressAdapter?.setList(list)
        binding.infoList.adapter = addressAdapter
        binding.infoList.layoutManager = LinearLayoutManager(requireContext())
        addressAdapter?.addChildClickViewIds(R.id.btn_del, R.id.btn_edit)
        addressAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.btn_edit -> {
                    sharedViewModel.currentModifyGoodsInfo.value =
                        adapter.getItem(position) as GoodsInfo
                    replaceChildrenFragment(ModifyGoodsFragment(), R.id.ly_container)
                }
                R.id.btn_del -> {
                    (requireActivity().application as AndroidApplication).applicationComponent.goodsDaoRepository()
                        .deleteGoods(
                            userModelDataMapper.GoodInfo2GoodEntity(
                                adapter.getItem(position) as GoodsInfo
                            )
                        )
                    sharedViewModel.isAddOrModifiedGoods.postValue(true)

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}