package com.example.ktapp.modules;

import com.example.ktapp.bean.AutoEngine;
import com.example.ktapp.bean.Engine;

import dagger.Binds;
import dagger.Module;

/**
 * @author wuli
 * 去除@Inject
 *  @Inject
 *  *     public AutoEngine() {
 *  *     }
 *  提供AutoEngineModule
 */
@Module(includes = AutoEngineModule.class)
public interface IEngineBindsAndProviderModule {
    @Binds
    Engine getAutoEngine(AutoEngine autoEngine);
}
