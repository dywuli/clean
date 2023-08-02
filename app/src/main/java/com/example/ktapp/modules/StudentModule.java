package com.example.ktapp.modules;

import com.example.ktapp.bean.Student;
import com.example.ktapp.bean.Teacher;

import dagger.Module;
import dagger.Provides;

/**
 * @author wuli
 */
@Module
public class StudentModule {
    @Provides
    Student providerStudent(Teacher teacher){
        return new Student(teacher);
    }
}
