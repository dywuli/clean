package com.example.ktapp.components;

import com.example.ktapp.modules.TeacherModule;

import dagger.Component;

/**
 * @author wuli
 */

@Component(modules = TeacherModule.class)
public interface TeacherStubComponent {
    StudentStubComponent.Builder buildStudentStubComponent();
}
