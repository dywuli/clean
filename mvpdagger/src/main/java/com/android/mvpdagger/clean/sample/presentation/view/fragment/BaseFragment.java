/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.android.mvpdagger.clean.sample.presentation.view.fragment;

import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.mvpdagger.clean.sample.presentation.internal.di.HasComponent;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Base {@link Fragment} class for every fragment in this application.
 */
public abstract class BaseFragment extends Fragment {
    protected Unbinder unbinder = null;

    /**
     * Shows a {@link Toast} message.
     *
     * @param message An string representing a message to be shown.
     */
    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gets a component for dependency injection by its type.
     */
    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    protected void butterKnifeBinView(View view) {
        unbinder = ButterKnife.bind(this, view);
    }
}
