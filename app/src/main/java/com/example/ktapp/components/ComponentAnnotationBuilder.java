package com.example.ktapp.components;

import com.example.ktapp.PagerActivity;
import com.example.ktapp.bean.Student;
import com.example.ktapp.bean.Teacher;
import com.example.ktapp.modules.TeacherModule;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author wuli
 */
@Component(modules = TeacherModule.class)
public interface ComponentAnnotationBuilder {
    void inject(PagerActivity activity);
    Student st();
    Teacher tt();
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder setTeacherModule1(TeacherModule teacherModule);
        @BindsInstance
        Builder setStudent(Student student);

        ComponentAnnotationBuilder build();
    }

}
