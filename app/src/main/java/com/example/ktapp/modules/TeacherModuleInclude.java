package com.example.ktapp.modules;

import com.example.ktapp.bean.Teacher;

import dagger.Module;
import dagger.Provides;

/**
 * @author wuli
 */
@Module
public class TeacherModuleInclude {
    @Provides
    Teacher providerParent() {
        return new Teacher();
    }
}
