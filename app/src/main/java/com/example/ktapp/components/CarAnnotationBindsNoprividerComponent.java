package com.example.ktapp.components;

import com.example.ktapp.bean.Car;
import com.example.ktapp.modules.IEngineBindsAndNoProviderModule;

import dagger.Component;

@Component(modules = IEngineBindsAndNoProviderModule.class)
public interface CarAnnotationBindsNoprividerComponent {
    Car car();
}