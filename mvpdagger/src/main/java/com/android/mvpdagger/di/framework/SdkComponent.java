package com.android.mvpdagger.di.framework;


import android.app.Application;

import com.android.mvpdagger.di.framework.module.ActivityModule;
import com.android.mvpdagger.di.framework.module.AppModule;
import com.android.mvpdagger.di.framework.module.BroadcastReceiverModule;
import com.android.mvpdagger.di.framework.module.FragmentModule;
import com.android.mvpdagger.di.framework.module.MvpModule;
import com.android.mvpdagger.di.framework.module.NormalObjModule;
import com.android.mvpdagger.di.framework.module.ServiceModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * 库模块中的Root Component
 */
@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AppModule.class,
                ActivityModule.class,
                FragmentModule.class,
                ServiceModule.class,
                BroadcastReceiverModule.class,
                NormalObjModule.class,
                // MVP 组件
                MvpModule.class
        }
)
public interface SdkComponent {

    void inject(SdkContainer sdkContainer);

    MvpModule.MvpComponent.Builder mvpComponentBuilder();

    @Component.Builder
    interface Builder {

        /**
         * 用于提供 Application 对象
         *
         * @param application Application
         * @return Builder
         */
        @BindsInstance
        Builder application(Application application);


        SdkComponent build();
    }

}
