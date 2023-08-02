package com.android.mvpdagger.di.test.mvp;

public interface BaseView {
    /**
     * 显示loading控件
     */
    void showLoading();

    /**
     * 关闭loading控件
     */
    void dismissLoading();
}
