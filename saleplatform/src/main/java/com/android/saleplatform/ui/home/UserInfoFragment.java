package com.android.saleplatform.ui.home;

import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_COMPLETE;
import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_PAY;
import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_TAKE;
import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_TAK_AND_PAY;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.saleplatform.R;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.databinding.FragmentAddrUserListBinding;
import com.android.saleplatform.internal.di.components.UserComponent;
import com.android.saleplatform.presenter.UserInfoPresenter;
import com.android.saleplatform.ui.BaseFragment;
import com.android.saleplatform.ui.adapter.UserAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;


public class UserInfoFragment extends BaseFragment {

    @Inject
    public UserInfoPresenter userInfoPresenter;

    @Inject
    public UserAdapter userAdapter;

    private FragmentAddrUserListBinding binding;
    private UserInfoViewModel userListViewModel;
    private View rootView;
    private AddressInfo curAddrInfo;
    private List<UserInfo> userInfoList;

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
            userListViewModel =
                    new ViewModelProvider(this).get(UserInfoViewModel.class);

            binding = FragmentAddrUserListBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();

            binding.btnBack.setOnClickListener(v -> {
                UserInfoFragment.this.back();
            });
            binding.goodStatusCompleted.setOnClickListener(v -> {
                String key = userListViewModel.getUserName().getValue();
                if (binding.goodStatusCompleted.isChecked()) {
                    userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 1, 0);
                } else {
                    userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 0, 0);
                }
            });

            binding.goodStatusPayed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    reqUserInfos();
                }
            });
            binding.goodStatusTake.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    reqUserInfos();
                }
            });
            binding.textName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    userListViewModel.getUserName().setValue(s.toString());
                }
            });

            userInfoPresenter.setViewModel(userListViewModel);
        }
        initObserve(userListViewModel);
        return rootView;
    }


    private void reqUserInfos() {
        if (curAddrInfo != null) {
            String key = userListViewModel.getUserName().getValue();
            boolean isPayed = binding.goodStatusPayed.isChecked();
            boolean isTaken = binding.goodStatusTake.isChecked();
            if (isPayed && isTaken) {
                binding.goodStatusCompleted.setChecked(true);
            } else {
                binding.goodStatusCompleted.setChecked(false);
            }
            boolean isCompleted = binding.goodStatusCompleted.isChecked();
            if (!isCompleted && !isTaken && !isPayed) {
                userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 0, 0);
            } else if (isCompleted) {
                userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 1, 0);
            } else {
                userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_TAK_AND_PAY, isTaken ? 1 : 0, isPayed ? 1 : 0);
            }
        }
    }

    private void initObserve(UserInfoViewModel viewModel) {
        viewModel.getUserInfos().observe(this.getViewLifecycleOwner(), addressWithUserInfos -> initView(addressWithUserInfos));
//        sharedViewModel.currentGoodsInfo.observeSticky(getViewLifecycleOwner(), goodsInfo -> {
//            curGoodInfo = goodsInfo;
//            show(curGoodInfo, curAddrInfo);
//        });
        sharedViewModel.currentAddressInfo.observeSticky(getViewLifecycleOwner(), addressInfo -> {
            curAddrInfo = addressInfo;
            show(curAddrInfo);
        });
        userListViewModel.getUserName().observe(getViewLifecycleOwner(), key -> {
            initData();
        });
        userListViewModel.getAllUses().observe(getViewLifecycleOwner(), userInfoList1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.txtCompletedCount.setText("" + userInfoList1.stream().filter(userInfo -> userInfo.getCompletedStatus() == 1).count());
                binding.txtUncompletedCount.setText("" + userInfoList1.stream().filter(userInfo -> userInfo.getCompletedStatus() == 0).count());
                binding.txtTotalCount.setText("" + userInfoList1.size());
            }
        });
    }

    private void show(AddressInfo addressInfo) {
        if (addressInfo != null) {
            binding.textAddressGoods.setText("地址:" + addressInfo.getAddressName());
            initData();
        }
    }

    private void initData() {
        boolean isPayed = binding.goodStatusPayed.isChecked();
        boolean isTaken = binding.goodStatusTake.isChecked();
        boolean isCompleted = binding.goodStatusCompleted.isChecked();
        String key = userListViewModel.getUserName().getValue();
        if (!isCompleted && !isTaken && !isPayed) {
            userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 0, 0);
        } else if (isCompleted) {
            userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_COMPLETE, 1, 0);
        } else {
            userInfoPresenter.getGetUserListByStatusAndAddress(key == null ? "" : key, curAddrInfo, TYPE_TAK_AND_PAY, isTaken ? 1 : 0, isPayed ? 1 : 0);
        }
        userInfoPresenter.getGetUserListByAddressIdGoodsId(curAddrInfo.getAddressId());
    }


    private void initView(List<UserInfo> userInfoList) {
        this.userInfoList = userInfoList;
        userAdapter.setList(userInfoList);
        binding.userInfoList.setAdapter(userAdapter);
        binding.userInfoList.setLayoutManager(new LinearLayoutManager(requireContext()));
        userAdapter.addChildClickViewIds(R.id.good_status_payed, R.id.good_status_take, R.id.good_status_completed);
        userAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                int status = 0;
                UserInfo userInfo = userInfoList.get(position);
                switch (view.getId()) {
                    case R.id.good_status_payed:
                        status = userInfo.getPayStatus();
                        if (status == 0) {
                            status = 1;
                        } else {
                            status = 0;
                        }
                        userInfo.setPayStatus(status);
                        break;
                    case R.id.good_status_take:
                        status = userInfo.getTakeStatus();
                        if (status == 0) {
                            status = 1;
                        } else {
                            status = 0;
                        }
                        userInfo.setTakeStatus(status);
                        break;
                    case R.id.good_status_completed:
                        showToastMessage("无需修改");
                    default:
                        break;
                }
                if (userInfo.getPayStatus() == 1 && userInfo.getTakeStatus() == 1) {
                    userInfo.setCompletedStatus(1);
                } else {
                    userInfo.setCompletedStatus(0);
                }
                userInfoPresenter.updateUser(userInfo);
                userInfoPresenter.getGetUserListByAddressIdGoodsId(curAddrInfo.getAddressId());
                adapter.notifyItemChanged(position);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}