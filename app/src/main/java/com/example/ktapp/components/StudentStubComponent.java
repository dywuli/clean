package com.example.ktapp.components;

import com.example.ktapp.PagerActivity;
import com.example.ktapp.modules.StudentModule;

import dagger.Subcomponent;

/**
 * @author wuli
 */
@Subcomponent(modules = StudentModule.class)
public interface StudentStubComponent {
    void inject(PagerActivity activity);
    @Subcomponent.Builder
    interface Builder{
        StudentStubComponent build();
    }
}
