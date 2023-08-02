package com.example.ktapp.modules;

import com.example.ktapp.bean.Engine;

import dagger.Module;
import dagger.Provides;

@Module
public class EngineMoudle {
    @Provides
    public Engine getEngine(){
        return new Engine();
    }
}
