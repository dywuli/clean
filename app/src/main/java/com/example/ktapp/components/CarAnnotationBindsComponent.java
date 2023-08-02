package com.example.ktapp.components;

import com.example.ktapp.bean.Car;
import com.example.ktapp.modules.IEngineBindsAndProviderModule;

import dagger.Component;

@Component(modules = IEngineBindsAndProviderModule.class)
public interface CarAnnotationBindsComponent {
    Car car();
}