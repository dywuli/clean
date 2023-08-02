package com.example.ktapp.modules;

import com.example.ktapp.bean.Teacher;
import com.example.ktapp.components.StudentStubComponent;

import dagger.Module;
import dagger.Provides;

/**
 * @author wuli
 */
// 在@Module注解中指定subcomponents，相当于该Module所提供的对象都是能够在StudentStubComponent所关联的Module中获取的
@Module(subcomponents = StudentStubComponent.class)
public class TeacherStubModule {
    @Provides
    Teacher providerParent() {
        return new Teacher();
    }
}
