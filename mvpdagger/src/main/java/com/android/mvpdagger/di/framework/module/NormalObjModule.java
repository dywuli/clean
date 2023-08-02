package com.android.mvpdagger.di.framework.module;

import com.android.mvpdagger.di.test.TestObj;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class NormalObjModule {

    @Provides
    static TestObj providerTestOjb() {
        return new TestObj();
    }
}
