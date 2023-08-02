package com.android.saleplatform.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.saleplatform.R;
import com.android.saleplatform.beans.AddressWithUserInfos;
import com.android.saleplatform.databinding.FragmentHomeBinding;
import com.android.saleplatform.internal.di.components.UserComponent;
import com.android.saleplatform.presenter.HomePresenter;
import com.android.saleplatform.ui.BaseFragment;
import com.android.saleplatform.ui.adapter.GoodsAdapter;
import com.android.saleplatform.ui.adapter.MyArrayAdapter;
import com.android.saleplatform.ui.view.CustomSpinner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class HomeFragment extends BaseFragment {

    @Inject
    public HomePresenter homePresenter;

    @Inject
    public GoodsAdapter goodsAdapter;

    private FragmentHomeBinding binding;
    private List<String> goodsInfoList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private HomeViewModel homeViewModel;
    private View rootView;
    private volatile int isOperaSpinnerView = 0;//0:init;1:open;2close;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            binding = FragmentHomeBinding.inflate(inflater, container, false);
            homeViewModel =
                    new ViewModelProvider(this).get(HomeViewModel.class);


            homePresenter.setViewModel(homeViewModel);

            rootView = binding.getRoot();
            binding.btnSearch.setOnClickListener(v -> {
                String key = binding.editTxt.getEditableText().toString();
                sharedViewModel.currentKey.setValue(key);

            });
            binding.editTxt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    sharedViewModel.currentKey.setValue(s.toString());
                }
            });
        }


        initSpinnerView();
        initObserve(homeViewModel);
        initData();
        return rootView;
    }

    private void initObserve(HomeViewModel viewModel) {
        viewModel.getAddressInfoWithUsersList().observe(this.getViewLifecycleOwner(), addressWithUserInfos -> initView(addressWithUserInfos));
//        viewModel.getGoodsInfos().observe(this.getViewLifecycleOwner(), goodsInfoList -> initSpinnerView(goodsInfoList));
        sharedViewModel.isAddOrModifiedGoods.observeSticky(getViewLifecycleOwner(), it -> {
            if (it) {
                String key = sharedViewModel.currentKey.getValue();
                if (key != null) {
                    search(key);
                }
            }
        });
        sharedViewModel.isAddOrModifiedAddress.observeSticky(getViewLifecycleOwner(), it -> {
            if (it) {
                String key = sharedViewModel.currentKey.getValue();
                if (key != null) {
                    search(key);
                }
            }
        });
        sharedViewModel.currentKey.observeSticky(getViewLifecycleOwner(), s -> {
            search(s);
        });

    }

    private void search(String key) {
        if (!Objects.equals(0, sharedViewModel.searchType.getValue())) {
            homePresenter.getUserListGroupByAddrByAddressName(key);
        } else {
            homePresenter.getAddressWithUserByGoodsName(key);
        }
    }

    private void initData() {
        String key = sharedViewModel.currentKey.getValue();
        search(key == null ? "" : key);
    }

    private void initSpinnerView() {
        if (adapter == null) {
            goodsInfoList.add("地址检索");
            goodsInfoList.add("商品检索");
            isOperaSpinnerView = 0;
            adapter = new MyArrayAdapter(requireContext(), R.layout.spinner_drop_down, goodsInfoList);
            adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
            binding.spinner1.setAdapter(adapter);
            binding.spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (isOperaSpinnerView == 2 || !Objects.equals(sharedViewModel.searchType.getValue(), position)) {
                        if (goodsAdapter != null) {
                            goodsAdapter.getData().clear();
                            goodsAdapter.notifyDataSetChanged();
                        }
                        sharedViewModel.currentKey.setValue("");
                        sharedViewModel.searchType.postValue(position);
                    }

                    // 一定要设置父视图可见，否则 在选择后，Spinner会消失
                    parent.setVisibility(View.VISIBLE);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
            binding.spinner1.setSpinnerEventsListener(new CustomSpinner.OnSpinnerEventsListener() {
                @Override
                public void onSpinnerOpened() {
                    isOperaSpinnerView = 1;
                }

                @Override
                public void onSpinnerClosed() {
                    isOperaSpinnerView = 2;
//                    initData();
                }
            });
        }
    }

    private void initView(List<AddressWithUserInfos> addrWithUsersList) {
        goodsAdapter.setList(addrWithUsersList);
        binding.goodsList.setAdapter(goodsAdapter);
        binding.goodsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        goodsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                AddressWithUserInfos addressWithUserInfos = (AddressWithUserInfos) adapter.getItem(position);
                sharedViewModel.currentAddressInfo.setValue(addressWithUserInfos.getAddressInfo());
                sharedViewModel.currentAddressWithUserInfosInfo.setValue(addressWithUserInfos);
                replaceChildrenFragment(new UserInfoFragment(), R.id.ly_container);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        binding = null;
    }
}