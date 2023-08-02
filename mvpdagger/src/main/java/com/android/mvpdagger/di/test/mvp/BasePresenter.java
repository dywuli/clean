package com.android.mvpdagger.di.test.mvp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

public class BasePresenter<V extends BaseView> implements LifecycleEventObserver {

    private final V mView;

    private final Context mContext;

    //获取当前生命周期
    public Lifecycle.Event mLifecycleEvent;

    public BasePresenter(Context context, V view) {
        mContext = context;
        mView = view;
    }

    public V getView() {
        return mView;
    }

    public Context getContext() {
        return mContext;
    }

    public Lifecycle.Event getLifecycleEvent() {
        return mLifecycleEvent;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        mLifecycleEvent = event;
    }


}
