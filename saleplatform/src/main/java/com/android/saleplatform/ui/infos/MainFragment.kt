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
import com.android.saleplatform.databinding.FragmentMainInfoBinding
import com.android.saleplatform.internal.di.components.UserComponent
import com.android.saleplatform.presenter.OrderInfoMaintainPresenter
import com.android.saleplatform.ui.BaseFragment
import com.android.saleplatform.ui.adapter.GoodsAdapter
import com.android.saleplatform.ui.adapter.MyArrayAdapter1
import com.android.saleplatform.ui.view.CustomSpinner.OnSpinnerEventsListener
import javax.inject.Inject

class MainFragment : BaseFragment() {

    private var binding: FragmentMainInfoBinding? = null
    private var rootView: View? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            super.onCreateView(inflater, container, savedInstanceState)
            binding = FragmentMainInfoBinding.inflate(inflater, container, false)
            rootView = binding!!.root
            binding!!.lyName.setOnClickListener {
                replaceChildrenFragment(AddressListFragment(), R.id.ly_container)
            }
            binding!!.lyGood.setOnClickListener {
                replaceChildrenFragment(GoodsListFragment(), R.id.ly_container)
            }
            binding!!.lyUser.setOnClickListener {
                replaceChildrenFragment(AddUserInfoFragment(), R.id.ly_container)
            }
        }

        return rootView
    }


    override fun onDestroyView() {
        super.onDestroyView()
//        binding = null;
    }
}