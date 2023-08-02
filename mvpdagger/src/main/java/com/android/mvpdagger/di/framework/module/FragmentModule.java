package com.android.mvpdagger.di.framework.module;

import com.android.mvpdagger.di.test.TestDiFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
* Fragment 组件注册
* @author hanlyjiang on 2022/3/5-11:16 PM
* @version 1.0
*/
@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    TestDiFragment contributeTestDiFragment();
}
