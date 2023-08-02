package com.android.mvpdagger.di.framework.module;

import com.android.mvpdagger.di.test.TestDiMainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    TestDiMainActivity contributeTestDiMainActivity();

}
