package com.example.ktapp.modules;

import com.example.ktapp.bean.Student;
import com.example.ktapp.bean.Teacher;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author wuli
 */
@Module(includes = TeacherModule.class)
public class StudentModuleInclude {
    @Singleton
    @Provides
    static Student providerStudent(Teacher teacher){
        return new Student(teacher);
    }
}
