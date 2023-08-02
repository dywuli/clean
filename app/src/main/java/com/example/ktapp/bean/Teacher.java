package com.example.ktapp.bean;

import android.util.Log;

/**
 * @author wuli
 */
public class Teacher {
    public void log() {
        Log.e("wuli", this.getClass().getSimpleName() + this);
    }
}
