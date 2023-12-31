package com.android.mvpdagger.di.framework.module;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.mvpdagger.di.framework.ActivityContext;
import com.android.mvpdagger.di.framework.mvp.AndroidProvider;
import com.android.mvpdagger.di.framework.mvp.MvpContainer;
import com.android.mvpdagger.di.framework.scope.MvpScope;
import com.android.mvpdagger.di.test.TestDiMvpActivity;
import com.android.mvpdagger.di.test.TestDiMvpActivityPresenter;
import com.android.mvpdagger.di.test.TestDiMvpFragment;
import com.android.mvpdagger.di.test.TestDiMvpFragmentPresenter;
import com.android.mvpdagger.di.test.TestMvpScope;

import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;

/**
 * MVP 组件注册
 *
 * @author hanlyjiang on 2022/3/5-11:16 PM
 * @version 1.0
 */
@Module(subcomponents = {
        MvpModule.MvpComponent.class,
})
public interface MvpModule {
    @Module
    abstract class MvpProvider {

        /**
         * 提供 ActivityContext
         * @param activityProvider AndroidProvider<Activity>
         * @return ActivityContext
         */
        @MvpScope
        @Provides
        @ActivityContext
        static Context bindActivityContext(@Nullable AndroidProvider<Activity> activityProvider) {
            if (activityProvider != null) {
                return activityProvider.get();
            } else {
                throw new NullPointerException("Please provider activity provider when build component!");
            }
        }

        @MvpScope
        @Provides
        static TestDiMvpActivityPresenter.TestDiView bindTestDiView(@Nullable AndroidProvider<Activity> activityProvider) {
            if (activityProvider != null) {
                return (TestDiMvpActivityPresenter.TestDiView) activityProvider.get();
            } else {
                throw new NullPointerException("Please provider activity provider when build component!");
            }
        }

        /**
         * 提供 TestDiMvpActivity 的注入接口
         *
         * @return TestDiMvpActivity
         */
        @ContributesAndroidInjector
        abstract TestDiMvpActivity contributeTestDiMvpActivity();

        @MvpScope
        @Provides
        static TestDiMvpFragmentPresenter.View bindTestTestDiMvpFragmentView(@Nullable AndroidProvider<Fragment> fragmentAndroidProvider) {
            // May delete null check to keep code clean since we provide Fragment correctly
//            if (fragmentAndroidProvider != null) {
            return (TestDiMvpFragmentPresenter.View) fragmentAndroidProvider.get();
//            } else {
//                throw new NullPointerException("Please provider Fragment provider when build component!");
//            }
        }

        /**
         * 提供 TestDiMvpFragment 的注入接口
         *
         * @return TestDiMvpFragment
         */
        @ContributesAndroidInjector
        abstract TestDiMvpFragment contributeTestDiMvpFragment();

    }


    @MvpScope
    @Subcomponent(modules = {
            AndroidInjectionModule.class,
            MvpProvider.class,
    })
    interface MvpComponent {

        MvpContainer inject(MvpContainer mvpContainer);

        TestMvpScope testMvpScope();

        @Subcomponent.Builder
        interface Builder {

            @BindsInstance
            Builder activityProvider(@Nullable AndroidProvider<Activity> activityProvider);

            @BindsInstance
            Builder fragmentProvider(@Nullable AndroidProvider<Fragment> fragmentProvider);

            MvpComponent build();
        }
    }


}
