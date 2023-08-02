/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.saleplatform;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;

import com.android.saleplatform.internal.di.components.ApplicationComponent;
import com.android.saleplatform.internal.di.components.DaggerApplicationComponent;
import com.android.saleplatform.internal.di.modules.ApplicationModule;

/**
 * Android Main Application
 * @author wuli
 */
public class AndroidApplication extends Application implements ViewModelStoreOwner {

    private ViewModelStore mAppViewModelStore;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
        this.initializeLeakDetection();
        mAppViewModelStore = new ViewModelStore();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

    private void initializeLeakDetection() {
//    if (BuildConfig.DEBUG && !AppWatcher.INSTANCE.isInstalled()) {
//      AppWatcher.INSTANCE.manualInstall(this);
//    }
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }
}
