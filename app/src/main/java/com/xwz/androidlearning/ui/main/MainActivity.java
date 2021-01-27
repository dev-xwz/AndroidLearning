package com.xwz.androidlearning.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xwz.androidlearning.R;
import com.xwz.androidlearning.databinding.ActivityMainBinding;

import java.util.Observable;
import java.util.Observer;

/**
 * @author 柯拉松
 * 首页 Observer观察者角色 唯一方法update，被观察者需要调用addObserver()方法，添加它的观察者列表
 */
public class MainActivity extends AppCompatActivity implements Observer {
    public static Window getMainWindow;
    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.WRITE_SECURE_SETTINGS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupListView(binding.rcvMain);
        setupObserver(mainViewModel);
        getMainWindow = getWindow();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            requestPermissions();
        }
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context,MainActivity.class);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        binding.setMainViewModel(mainViewModel);
    }

    private void setupListView(RecyclerView rcvMain) {
        MainAdapter mainAdapter = new MainAdapter();
        mainAdapter.setMainModelList(mainViewModel.getMainModelList());
        rcvMain.setAdapter(mainAdapter);
        rcvMain.setHasFixedSize(true);
    }

    public void setupObserver(Observable observable) {
        observable.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {
        if (observable instanceof MainViewModel) {
            MainAdapter mainAdapter = (MainAdapter) binding.rcvMain.getAdapter();
            MainViewModel mainViewModel = (MainViewModel) observable;
            if (mainAdapter != null) {
                mainAdapter.setMainModelList(mainViewModel.getMainModelList());
            }
        }
    }

    /**
     * 保存海报图片
     */
    private static final int REQUEST_STATE_CODE = 1010;

    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_STATE_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_STATE_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "权限授予成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "权限授予失败，请重新授予", Toast.LENGTH_LONG).show();
                    return;
                }
                break;
            default:
                break;
        }
    }
}
