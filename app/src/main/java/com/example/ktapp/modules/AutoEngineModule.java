package com.example.ktapp.modules;

import com.example.ktapp.bean.AutoEngine;

import dagger.Module;
import dagger.Provides;
@Module
public class AutoEngineModule{

    @Provides AutoEngine getAutoEngine(){
        return new AutoEngine();
    }
}
