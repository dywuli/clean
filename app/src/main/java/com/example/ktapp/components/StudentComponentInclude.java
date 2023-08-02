package com.example.ktapp.components;

import com.example.ktapp.PagerActivity;
import com.example.ktapp.modules.StudentModuleInclude;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author wuli
 */
@Singleton
@Component(modules = StudentModuleInclude.class)
public interface StudentComponentInclude {
    void inject(PagerActivity activity);
}
