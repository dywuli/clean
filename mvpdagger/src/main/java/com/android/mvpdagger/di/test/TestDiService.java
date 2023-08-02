package com.android.mvpdagger.di.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.android.mvpdagger.di.framework.Injectable;
import com.android.mvpdagger.di.framework.SdkAndroidInjection;

import javax.inject.Inject;

public class TestDiService extends Service implements Injectable {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    public TestDiService() {
    }

    @Override
    public void onCreate() {
        SdkAndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable @org.jetbrains.annotations.Nullable Intent intent, int flags, int startId) {
        Test.assertInject(this, testSingleton, testObj);
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}