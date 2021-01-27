package com.xwz.androidlearning;

import android.app.Application;
import android.content.Context;

import com.xwz.androidlearning.injection.component.ApplicationComponent;
import com.xwz.androidlearning.injection.component.DaggerApplicationComponent;
import com.xwz.androidlearning.injection.module.ApplicationModule;

/**
 * @author 柯拉松
 */
public class MyApplication extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public ApplicationComponent getComponent(){
        if (mApplicationComponent == null){
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
