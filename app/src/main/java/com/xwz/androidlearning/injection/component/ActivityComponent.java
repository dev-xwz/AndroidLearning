package com.xwz.androidlearning.injection.component;

import com.xwz.androidlearning.injection.module.ActivityModule;
import com.xwz.androidlearning.injection.scope.InActivity;
import com.xwz.androidlearning.ui.splash.SplashActivity;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link com.xwz.androidlearning.MyApplication} for the list of Dagger components
 * used in this application.
 */
@InActivity
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(SplashActivity activity);

}
