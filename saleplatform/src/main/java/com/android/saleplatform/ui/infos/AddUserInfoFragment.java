package com.android.saleplatform.ui.infos;

import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_COMPLETE;
import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_TAK_AND_PAY;
import static com.android.saleplatform.domain.interactor.GetUserListByStatus.TYPE_TAK_OR_PAY;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.saleplatform.R;
import com.android.saleplatform.beans.AddressInfo;
import com.android.saleplatform.beans.GoodsInfo;
import com.android.saleplatform.beans.UserInfo;
import com.android.saleplatform.databinding.FragmentAddUserListBinding;
import com.android.saleplatform.internal.di.components.UserComponent;
import com.android.saleplatform.presenter.UserInfoPresenter;
import com.android.saleplatform.ui.BaseFragment;
import com.android.saleplatform.ui.adapter.UserAdapter;
import com.android.saleplatform.ui.home.UserInfoViewModel;
import com.android.saleplatform.ui.nicedialog.BaseNiceDialog;
import com.android.saleplatform.ui.nicedialog.CommonDialogUtil;
import com.android.saleplatform.ui.nicedialog.ViewConvertListener;
import com.android.saleplatform.ui.nicedialog.ViewHolder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;

import java.util.List;

import javax.inject.Inject;


public class AddUserInfoFragment extends BaseFragment {

    @Inject
    public UserInfoPresenter userInfoPresenter;
    @Inject
    public UserAdapter userAdapter;

    private FragmentAddUserListBinding binding;
    private UserInfoViewModel userListViewModel;
    private View rootView;
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

            binding = FragmentAddUserListBinding.inflate(inflater, container, false);
            rootView = binding.getRoot();

            binding.btnBack.setOnClickListener(v -> {
                AddUserInfoFragment.this.back();
            });

            binding.btnModifyStatus.setOnClickListener(v -> {
                replaceChildrenFragment(new AddUserFragment(), R.id.ly_container);
            });
            userInfoPresenter.setViewModel(userListViewModel);

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
            binding.goodStatusUncompleted.setChecked(true);
            binding.goodStatusCompleted.setOnClickListener(v -> {
                binding.goodStatusUncompleted.setChecked(false);
                binding.goodStatusAll.setChecked(false);
                reqUserInfos();
            });
            binding.goodStatusUncompleted.setOnClickListener(v -> {
                binding.goodStatusCompleted.setChecked(false);
                binding.goodStatusAll.setChecked(false);
                reqUserInfos();
            });
            binding.goodStatusAll.setOnClickListener(v -> {
                binding.goodStatusCompleted.setChecked(true);
                binding.goodStatusUncompleted.setChecked(true);
                reqUserInfos();
            });
        }

        initObserve(userListViewModel);
        initData();
        return rootView;
    }

    private void reqUserInfos() {
        String key = userListViewModel.getUserName().getValue();
        boolean isPayed = binding.goodStatusCompleted.isChecked();
        boolean isTaken = binding.goodStatusUncompleted.isChecked();
        if (isPayed && isTaken) {
            userInfoPresenter.getGetUserListByStatus(key == null ? "" : key, -1, 0, 0);
        } else if (isPayed) {
            userInfoPresenter.getGetUserListByStatus(key == null ? "" : key, TYPE_COMPLETE, 1, 0);
        } else if (isTaken) {
            userInfoPresenter.getGetUserListByStatus(key == null ? "" : key, TYPE_COMPLETE, 0, 0);

        }
    }

    private void initObserve(UserInfoViewModel viewModel) {
        viewModel.getUserInfos().observe(this.getViewLifecycleOwner(), addressWithUserInfos -> initView(addressWithUserInfos));

        sharedViewModel.isAddOrModifyUser.observeSticky(getViewLifecycleOwner(), aBoolean -> {
            if (aBoolean) {
                reqUserInfos();
            }
        });
        viewModel.getUserName().observe(getViewLifecycleOwner(), name -> {
            reqUserInfos();
        });
    }


    private void initData() {
        String name = userListViewModel.getUserName().getValue();
        if (name == null) {
            userInfoPresenter.getGetUserListByStatus("", TYPE_COMPLETE, 0, 0);
        }
    }


    private void initView(List<UserInfo> userInfoList) {
        Log.e("wuli", "******" + userInfoList.size());
        this.userInfoList = userInfoList;
        userAdapter.setList(this.userInfoList);
        binding.userInfoList.setAdapter(userAdapter);
        binding.userInfoList.setLayoutManager(new LinearLayoutManager(requireContext()));
        userAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                sharedViewModel.currentModifyUserInfo.setValue(userInfoList.get(position));
                replaceChildrenFragment(new ModifyUserFragment(), R.id.ly_container);
            }
        });
        userAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                CommonDialogUtil.showCustomDialogView(requireActivity(), R.layout.dialog_two_button, new ViewConvertListener() {
                    @Override
                    protected void convertView(ViewHolder holder, BaseNiceDialog dialog) {
                        holder.getView(R.id.atvConfirm).setOnClickListener(v -> {
                            userInfoPresenter.deleteUser(userInfoList.get(position));
                            sharedViewModel.isAddOrModifyUser.setValue(true);
                            dialog.dismissAllowingStateLoss();
                        });
                        holder.getView(R.id.atvCancel).setOnClickListener(v -> {
                            dialog.dismissAllowingStateLoss();
                        });
                    }
                });
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}