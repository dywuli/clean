package com.example.ktapp.components;

import com.example.ktapp.bean.Teacher;
import com.example.ktapp.modules.TeacherModule;

import dagger.Component;

/**
 * @author wuli
 */
@Component(modules = TeacherModule.class)
public interface TeacherComponent {
    Teacher teacher();
}
