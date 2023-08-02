package com.android.saleplatform.ui.infos

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.saleplatform.AndroidApplication
import com.android.saleplatform.databinding.FragmentAddAddressBinding
import com.android.saleplatform.mapper.Converters
import com.android.saleplatform.mapper.UserModelDataMapper
import com.android.saleplatform.presenter.OrderPresenter
import com.android.saleplatform.ui.BaseFragment
import javax.inject.Inject

class ModifyAddressFragment : BaseFragment() {
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

        binding.btnModifyStatus.text = "修改"
        binding.btnModifyStatus.setOnClickListener {
            if (updateAddress()) {
                sharedViewModel.isAddOrModifiedAddress.value = true
                back()
            } else {
                showToastMessage("请检查是否有空项未填")
            }
        }
        sharedViewModel.currentModifyAddressInfo.observeSticky(viewLifecycleOwner) {
            binding.textGoodName.setText(it.addressName)
        }
        return root
    }

    fun updateAddress(): Boolean {
        val address = binding.textGoodName.text.toString()
        if (!TextUtils.isEmpty(address)) {
            val addressInfo = sharedViewModel.currentModifyAddressInfo.value
            addressInfo?.addressName = address
            addressInfo?.updateDate = Converters.dateToTimestamp(null)
            var addressDao =
                (requireActivity().application as AndroidApplication).applicationComponent
                    .addressDaoRepository()
            addressDao.updateAddress(userModelDataMapper.AddressInfo2AddressEntity(addressInfo))

            return true
        }
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}