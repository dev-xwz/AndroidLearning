package com.xwz.androidlearning.injection.component;

import com.xwz.androidlearning.injection.module.ActivityModule;
import com.xwz.androidlearning.injection.module.FragmentModule;
import com.xwz.androidlearning.injection.scope.InFragment;

import dagger.Subcomponent;

/**
 * This is a Dagger component. Refer to {@link com.xwz.androidlearning.MyApplication} for the list of Dagger components
 * used in this application.
 */
@InFragment
@Subcomponent(modules = {ActivityModule.class, FragmentModule.class})
public interface FragmentComponent {

}
