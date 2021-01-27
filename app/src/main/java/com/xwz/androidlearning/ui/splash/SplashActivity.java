package com.xwz.androidlearning.ui.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.xwz.androidlearning.MyApplication;
import com.xwz.androidlearning.R;
import com.xwz.androidlearning.injection.component.ConfigPersistentComponent;
import com.xwz.androidlearning.injection.component.DaggerConfigPersistentComponent;
import com.xwz.androidlearning.injection.module.ActivityModule;
import com.xwz.androidlearning.injection.qualifier.ActivityContext;
import com.xwz.androidlearning.ui.main.MainActivity;
import com.xwz.androidlearning.utils.RxUtil;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author 柯拉松
 * 启动页，不继承AppCompatActivity，加载主题会导致界面启动卡顿
 */
public class SplashActivity extends Activity {

    private static final long DELAY = 2000L;
    private Disposable mDisposable;
    @Inject @ActivityContext Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inject instance for activity
        ConfigPersistentComponent component = DaggerConfigPersistentComponent.builder()
                .applicationComponent(MyApplication.get(this).getComponent()).build();
        component.activityComponent(new ActivityModule(this)).inject(this);
        // 让Layout延伸到StatusBar和NavigationBar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_splash);
        setUpSplash();
    }

    private void setUpSplash() {
        mDisposable = Observable.timer(DELAY, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        finishTask();
                    }
                });
    }

    private void finishTask() {
        startActivity(MainActivity.getStartIntent(mContext));
        finish();
    }

    @Override
    protected void onDestroy() {
        RxUtil.dispose(mDisposable);
        super.onDestroy();
    }
}
