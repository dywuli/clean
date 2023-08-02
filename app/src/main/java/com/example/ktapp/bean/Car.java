package com.example.ktapp.bean;

import android.util.Log;

import javax.inject.Inject;

public class Car {
    private Engine engine;

    @Inject
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void log() {
        Log.e("wuli", this.getClass().getSimpleName() + " " + engine);
    }
}
