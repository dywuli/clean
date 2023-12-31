package com.android.mvpdagger.di.test;

import android.content.Context;
import android.util.Log;

import com.android.mvpdagger.di.framework.ActivityContext;
import com.android.mvpdagger.di.test.mvp.BasePresenter;
import com.android.mvpdagger.di.test.mvp.BaseView;

import javax.inject.Inject;

public class TestDiMvpActivityPresenter extends BasePresenter<TestDiMvpActivityPresenter.TestDiView> {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    public void assertInject() {
        Test.assertInject(this, testSingleton, testObj);
        Log.d(getClass().getSimpleName(),"context = " + getContext()+"");
    }

    @Inject
    public TestDiMvpActivityPresenter(@ActivityContext Context context, TestDiView view) {
        super(context, view);
    }

    public interface TestDiView extends BaseView {
    }
}
