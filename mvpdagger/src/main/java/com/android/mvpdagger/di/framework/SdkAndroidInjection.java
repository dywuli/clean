package com.android.mvpdagger.di.framework;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.internal.Beta;

import static android.util.Log.DEBUG;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Injects core Android types. 由 AndroidInjection 类复制修改
 */
@Beta
public final class SdkAndroidInjection {
    private static final String TAG = "dagger.android";

    /**
     * Injects {@code activity} if an associated {@link AndroidInjector} implementation can be found,
     * otherwise throws an {@link IllegalArgumentException}.
     *
     * @throws RuntimeException if the {@link Application} doesn't implement {@link
     *                          HasAndroidInjector}.
     */
    public static void inject(Activity activity) {
        inject(activity, SdkInjector.getSdkContainer());
    }

    public static void inject(Activity activity, HasAndroidInjector androidInjector) {
        checkNotNull(activity, "activity");
        if (androidInjector == null) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            androidInjector.getClass().getCanonicalName(),
                            HasAndroidInjector.class.getCanonicalName()));
        }
        doInject(activity, (HasAndroidInjector) androidInjector);
    }

    /**
     * Injects {@code fragment} if an associated {@link AndroidInjector} implementation can be found,
     * otherwise throws an {@link IllegalArgumentException}.
     *
     * <p>Uses the following algorithm to find the appropriate {@code AndroidInjector<Fragment>} to
     * use to inject {@code fragment}:
     *
     * <ol>
     *   <li>Walks the parent-fragment hierarchy to find the a fragment that implements {@link
     *       HasAndroidInjector}, and if none do
     *   <li>Uses the {@code fragment}'s {@link Fragment#getActivity() activity} if it implements
     *       {@link HasAndroidInjector}, and if not
     *   <li>Uses the {@link Application} if it implements {@link HasAndroidInjector}.
     * </ol>
     * <p>
     * If none of them implement {@link HasAndroidInjector}, a {@link IllegalArgumentException} is
     * thrown.
     *
     * @throws IllegalArgumentException if no parent fragment, activity, or application implements
     *                                  {@link HasAndroidInjector}.
     */
    public static void inject(Fragment fragment) {
        inject(fragment, SdkInjector.getSdkContainer());
    }

    public static void inject(Fragment fragment, HasAndroidInjector androidInjector) {
        checkNotNull(fragment, "fragment");
        HasAndroidInjector hasAndroidInjector = findHasAndroidInjectorForFragment(fragment, androidInjector);
        if (Log.isLoggable(TAG, DEBUG)) {
            Log.d(
                    TAG,
                    String.format(
                            "An injector for %s was found in %s",
                            fragment.getClass().getCanonicalName(),
                            hasAndroidInjector.getClass().getCanonicalName()));
        }

        doInject(fragment, hasAndroidInjector);
    }

    private static HasAndroidInjector findHasAndroidInjectorForFragment(Fragment fragment, HasAndroidInjector androidInjector) {
        Fragment parentFragment = fragment;
        while ((parentFragment = parentFragment.getParentFragment()) != null) {
            if (parentFragment instanceof HasAndroidInjector) {
                return (HasAndroidInjector) parentFragment;
            }
        }
        Activity activity = fragment.getActivity();
        if (activity instanceof HasAndroidInjector) {
            return (HasAndroidInjector) activity;
        }
        if (androidInjector != null) {
            return (HasAndroidInjector) androidInjector;
        }
        throw new IllegalArgumentException(
                String.format("No injector was found for %s", fragment.getClass().getCanonicalName()));
    }

    /**
     * Injects {@code service} if an associated {@link AndroidInjector} implementation can be found,
     * otherwise throws an {@link IllegalArgumentException}.
     *
     * @throws RuntimeException if the {@link Application} doesn't implement {@link
     *                          HasAndroidInjector}.
     */
    public static void inject(Service service) {
        checkNotNull(service, "service");
        SdkContainer application = SdkInjector.getSdkContainer();
        if (application == null) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasAndroidInjector.class.getCanonicalName()));
        }

        doInject(service, (HasAndroidInjector) application);
    }

    /**
     * Injects {@code broadcastReceiver} if an associated {@link AndroidInjector} implementation can
     * be found, otherwise throws an {@link IllegalArgumentException}.
     *
     * @throws RuntimeException if the {@link Application} from {@link
     *                          Context#getApplicationContext()} doesn't implement {@link HasAndroidInjector}.
     */
    public static void inject(BroadcastReceiver broadcastReceiver, Context context) {
        checkNotNull(broadcastReceiver, "broadcastReceiver");
        checkNotNull(context, "context");
        SdkContainer application = SdkInjector.getSdkContainer();
        if (!(application instanceof HasAndroidInjector)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasAndroidInjector.class.getCanonicalName()));
        }

        doInject(broadcastReceiver, (HasAndroidInjector) application);
    }

    /**
     * Injects {@code contentProvider} if an associated {@link AndroidInjector} implementation can be
     * found, otherwise throws an {@link IllegalArgumentException}.
     *
     * @throws RuntimeException if the {@link Application} doesn't implement {@link
     *                          HasAndroidInjector}.
     */
    public static void inject(ContentProvider contentProvider) {
        checkNotNull(contentProvider, "contentProvider");
        SdkContainer application = SdkInjector.getSdkContainer();
        if (!(application instanceof HasAndroidInjector)) {
            throw new RuntimeException(
                    String.format(
                            "%s does not implement %s",
                            application.getClass().getCanonicalName(),
                            HasAndroidInjector.class.getCanonicalName()));
        }

        doInject(contentProvider, (HasAndroidInjector) application);
    }

    private static void doInject(Object target, HasAndroidInjector hasAndroidInjector) {
        AndroidInjector<Object> androidInjector = hasAndroidInjector.androidInjector();
        checkNotNull(
                androidInjector, "%s.androidInjector() returned null", hasAndroidInjector.getClass());

        androidInjector.inject(target);
    }

    private SdkAndroidInjection() {
    }
}
