package com.xwz.androidlearning;

import android.app.Application;

public class MyApplication extends Application {

    public static MyApplication mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }
}
