package com.xwz.androidlearning.injection.component;

import android.app.Application;
import android.content.Context;

import com.xwz.androidlearning.injection.module.ApplicationModule;
import com.xwz.androidlearning.injection.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    @ApplicationContext
    Context context();
    Application application();
}
