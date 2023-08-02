package com.example.ktapp.components;

import com.example.ktapp.PagerActivity;
import com.example.ktapp.bean.Student;

import dagger.BindsInstance;
import dagger.Component;

/**
 * @author wuli
 */
@Component
public interface ComponentAnnotationsFactoryComponent {
    void inject(PagerActivity activity);
    @Component.Factory
    interface Factory{
        ComponentAnnotationsFactoryComponent create(@BindsInstance Student student);
    }
}
