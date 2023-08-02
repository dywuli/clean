package com.example.ktapp;

import android.app.Application;

import com.example.ktapp.bean.Student;

import javax.inject.Inject;

public class App extends Application {
    @Inject
    Student student;
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
