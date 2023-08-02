package com.example.ktapp.components;

import com.example.ktapp.bean.Car;
import com.example.ktapp.modules.EngineMoudle;

import dagger.Component;

@Component(modules = EngineMoudle.class)
public interface CarComponent {
    Car car();
}