package com.example.ktapp.components;

import com.example.ktapp.PagerActivity;
import com.example.ktapp.modules.StudentModule;

import dagger.Component;

/**
 * @author wuli
 */
@Component(modules = StudentModule.class, dependencies = TeacherComponent.class)
public interface StudentComponent {
    void inject(PagerActivity activity);
}
