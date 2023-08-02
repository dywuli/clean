/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.android.saleplatform.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.android.saleplatform.AndroidApplication;
import com.android.saleplatform.internal.di.HasComponent;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder = null;
    protected SharedViewModel sharedViewModel;

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void butterKnifeBinView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private ViewModelProvider mFragmentProvider;
    private ViewModelProvider mActivityProvider;
    private ViewModelProvider mApplicationProvider;

    //TODO tip 1: DataBinding 严格模式（详见 DataBindingFragment - - - - - ）：
    // 将 DataBinding 实例限制于 base 页面中，默认不向子类暴露，
    // 通过这样的方式，来彻底解决 视图实例 null 安全的一致性问题，
    // 如此，视图实例 null 安全的安全性将和基于函数式编程思想的 Jetpack Compose 持平。

    // 如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910

    //TODO tip 2: Jetpack 通过 "工厂模式" 来实现 ViewModel 的作用域可控，
    //目前我们在项目中提供了 Application、Activity、Fragment 三个级别的作用域，
    //值得注意的是，通过不同作用域的 Provider 获得的 ViewModel 实例不是同一个，
    //所以如果 ViewModel 对状态信息的保留不符合预期，可以从这个角度出发去排查 是否眼前的 ViewModel 实例不是目标实例所致。

    //如果这样说还不理解的话，详见 https://xiaozhuanlan.com/topic/6257931840

    protected <T extends ViewModel> T getFragmentScopeViewModel(@NonNull Class<T> modelClass) {
        if (mFragmentProvider == null) {
            mFragmentProvider = new ViewModelProvider(this);
        }
        return mFragmentProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getActivityScopeViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(getActivity());
        }
        return mActivityProvider.get(modelClass);
    }

    protected <T extends ViewModel> T getApplicationScopeViewModel(@NonNull Class<T> modelClass) {
        if (mApplicationProvider == null) {
            mApplicationProvider = new ViewModelProvider((AndroidApplication) getActivity().getApplicationContext());
        }
        return mApplicationProvider.get(modelClass);
    }

    protected NavController nav() {
        return NavHostFragment.findNavController(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((LifecycleRegistry) getLifecycle()).setCurrentState(Lifecycle.State.STARTED);
    }
    protected Fragment replaceFragment(Fragment paramFragment, int resId) {
        FragmentTransaction localFragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment addedFragment = getChildFragmentManager().findFragmentByTag(paramFragment.getClass().getSimpleName());
        if (addedFragment == null) {
            addedFragment = paramFragment;
            localFragmentTransaction.replace(resId, paramFragment, paramFragment.getClass().getSimpleName());
        } else {
            localFragmentTransaction.show(addedFragment);
        }
        localFragmentTransaction.commitAllowingStateLoss();
        return addedFragment;
    }

    protected Fragment replaceChildrenFragment(Fragment paramFragment, int resId) {
        FragmentTransaction localFragmentTransaction = getChildFragmentManager().beginTransaction();
        Fragment addedFragment = getChildFragmentManager().findFragmentByTag(paramFragment.getClass().getSimpleName());
        if (addedFragment == null) {
            addedFragment = paramFragment;
            localFragmentTransaction.replace(resId, paramFragment, paramFragment.getClass().getSimpleName());
        } else {
            List<Fragment> fragments = addedFragment.getChildFragmentManager().getFragments();
            FragmentTransaction fragmentTransaction = addedFragment.getChildFragmentManager().beginTransaction();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    fragmentTransaction.remove(fragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
            localFragmentTransaction.show(addedFragment);
        }
        localFragmentTransaction.commitAllowingStateLoss();
        return addedFragment;
    }


    private void clearChildFragment(Fragment fragment) {
        if (fragment != null) {
            List<Fragment> fragments = fragment.getChildFragmentManager().getFragments();
            FragmentTransaction fragmentTransaction = fragment.getChildFragmentManager().beginTransaction();
            if (fragments != null && !fragments.isEmpty()) {
                for (Fragment f : fragments) {
                    fragmentTransaction.remove(f);
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        }
    }


    public void back() {
        Fragment fragment = this.getParentFragment();
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragment.getChildFragmentManager().beginTransaction();
            fragmentTransaction.remove(this);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
