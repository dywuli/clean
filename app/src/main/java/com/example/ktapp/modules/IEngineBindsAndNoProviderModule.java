package com.example.ktapp.modules;

import com.example.ktapp.bean.AutoEngine;
import com.example.ktapp.bean.Engine;

import dagger.Binds;
import dagger.Module;

/**
 * @author wuli
 * 添加@Inject
 * /// @Inject
 *     public AutoEngine() {
 *     }
 */
@Module
public interface IEngineBindsAndNoProviderModule {
    @Binds
    Engine getAutoEngine(AutoEngine autoEngine);
}
