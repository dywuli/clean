package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.databinding.FragmentAddGoodsBinding
import com.android.saleplatform.mapper.Converters
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.ui.BaseFragment

class ModifyGoodsFragment : BaseFragment() {
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
        binding.btnModifyStatus.text = "修改"
        binding.btnModifyStatus.setOnClickListener {
            if (updateGoods()) {
                back()
                sharedViewModel.isAddOrModifiedGoods.postValue(true)
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        sharedViewModel.currentModifyGoodsInfo.observeSticky(viewLifecycleOwner) {
            binding.textGoodName.setText(it.goodsName)
            binding.textGoodPrice.setText(it.price.toString())
        }
        return root
    }

    fun updateGoods(): Boolean {
        val name: String = binding.textGoodName.text.toString()
        val price: String = binding.textGoodPrice.text.toString()
        val goodsInfo = sharedViewModel.currentModifyGoodsInfo.value
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(price)) {
            goodsInfo?.price = price.toDouble()
            goodsInfo?.goodsName = name
            goodsInfo?.updateDate = Converters.dateToTimestamp(null)
            val goodDao = (requireActivity().application as AndroidApplication).applicationComponent
                .goodsDaoRepository()
            goodDao.updateGoods(
                userModelDataMapper
                    .GoodInfo2GoodEntity(
                        goodsInfo
                    )
            )

            return true;
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}