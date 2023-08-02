package com.android.saleplatform.ui.nicedialog;


import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentActivity;

public class CommonDialogUtil {

    public static void showCustomDialogView(FragmentActivity activity, @LayoutRes int layout, ViewConvertListener listener ) {
        if (activity == null || activity.isDestroyed()){
            return;
        }
        NiceDialog.init()
                .setLayoutId(layout)
                .setConvertListener(listener)
                .setDimAmount(0.3f)
                .setMargin(24)
                .setOutCancel(false)
                .show(activity.getSupportFragmentManager());
    }
    public static void showCustomDialogWithWidth(FragmentActivity activity, @LayoutRes int layout,int width, ViewConvertListener listener) {
        if (activity == null || activity.isDestroyed()){
            return;
        }
        NiceDialog.init()
                .setLayoutId(layout)
                .setConvertListener(listener)
                .setDimAmount(0.3f)
                .setWidth(width)
                .setOutCancel(false)
                .show(activity.getSupportFragmentManager());
    }
    public static void showCustomDialogWithWH(FragmentActivity activity, @LayoutRes int layout,int width, int height,ViewConvertListener listener) {
        if (activity == null || activity.isDestroyed()){
            return;
        }
        NiceDialog.init()
                .setLayoutId(layout)
                .setConvertListener(listener)
                .setDimAmount(0.3f)
                .setWidth(width)
                .setHeight(height)
                .setOutCancel(true)
                .show(activity.getSupportFragmentManager());
    }



}
