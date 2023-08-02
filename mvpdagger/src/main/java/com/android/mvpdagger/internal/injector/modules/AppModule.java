package com.android.mvpdagger.internal.injector.modules;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * @author wuli
 */
@Module(includes = {AndroidSupportInjectionModule.class, AndroidInjectionModule.class})
public abstract class AppModule {
    @Singleton
    @Provides
    static Context provideContext(Application application) {
        return application;
    }

}
