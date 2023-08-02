package com.android.mvpdagger.internal.injector.components;

import android.app.Application;

import com.android.mvpdagger.MyApp;
import com.android.mvpdagger.internal.injector.modules.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author wuli
 */
@Component(modules = {AppModule.class,})
@Singleton
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(MyApp app);
}
