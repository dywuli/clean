package com.android.mvpdagger.clean.sample.presentation.view.activity;

import android.app.Activity;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.mvpdagger.clean.sample.presentation.AndroidApplication;
import com.android.mvpdagger.clean.sample.presentation.internal.di.components.ApplicationComponent;
import com.android.mvpdagger.clean.sample.presentation.internal.di.modules.ActivityModule;
import com.android.mvpdagger.clean.sample.presentation.exception.navigation.Navigator;

import javax.inject.Inject;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    /**
     * Adds a {@link Fragment} to this activity's layout.
     *
     * @param containerViewId The container view to where add the fragment.
     * @param fragment        The fragment to be added.
     */
    protected void addFragment(int containerViewId, Fragment fragment) {
        final FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
