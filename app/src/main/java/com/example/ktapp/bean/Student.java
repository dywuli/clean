package com.example.ktapp.bean;

import android.util.Log;

/**
 * @author wuli
 */
public class Student {
    public Teacher teacher;
    public Student(Teacher teacher) {
        this.teacher = teacher;
    }
    public void log() {
        Log.e("wuli", this.getClass().getSimpleName() + this);
    }
}
