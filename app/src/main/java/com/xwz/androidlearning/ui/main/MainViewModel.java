package com.xwz.androidlearning.ui.main;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.xwz.androidlearning.MyApplication;
import com.xwz.androidlearning.utils.GetJsonDataUtil;

import org.json.JSONArray;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *  @author 柯拉松
 *  首页ViewModel
 *  Observable是被观察目标subject对象，当它的数据发生变化，调用setChanged()、notifyObserves()
 */
public class MainViewModel extends Observable {

    public ObservableInt mainRcv;
    public ObservableInt peopleLabel;
    private ComponentName defaultComponent;
    private ComponentName testComponent;
    private PackageManager packageManager;

    private List<MainModel> mainModelList;
    private Context context;

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.mainModelList = new ArrayList<>();
        mainRcv = new ObservableInt(View.VISIBLE);
        peopleLabel = new ObservableInt(View.VISIBLE);
        initComponent();
        initJsonData();
    }

    private void initComponent(){
        //拿到默认的组件
        defaultComponent = new ComponentName(MyApplication.get(context).getBaseContext(),"com.xwz.androidlearning.ui.splash.SplashActivity");
        //拿到我注册的别名test组件
        testComponent = new ComponentName(MyApplication.get(context).getBaseContext(), "main.copy");
        packageManager = MyApplication.get(context).getPackageManager();
    }

    private void initJsonData() {
        //注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件，关键逻辑在于循环体
        String jsonData = new GetJsonDataUtil().getJson(context, "maindata.json");
        ArrayList<MainModel> jsonBean = parseData(jsonData);
        changeMainDataSet(jsonBean);
    }

    public ArrayList<MainModel> parseData(String result) {
        ArrayList<MainModel> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                MainModel entity = gson.fromJson(data.optJSONObject(i).toString(), MainModel.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private void changeMainDataSet(List<MainModel> mainModels) {
        mainModelList.addAll(mainModels);
        setChanged();
        notifyObservers();
    }

    public List<MainModel> getMainModelList() {
        return mainModelList;
    }

    public void changeIcon(View view) {
        disableComponent(defaultComponent);
        enableComponent(testComponent);
    }

    public void changeDefaultIcon(View view) {
        enableComponent(defaultComponent);
        disableComponent(testComponent);
    }

    /**
     * 启用组件
     */
    private void enableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
            //已经启用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     */
    private void disableComponent(ComponentName componentName) {
        int state = packageManager.getComponentEnabledSetting(componentName);
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED) {
            //已经禁用
            return;
        }
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 保存海报
     * @param view view
     */
    public void savePoster(View view) {
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(screenshot);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        saveToLocal(screenshot);
    }

    public void saveToLocal(Bitmap bitmap) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME,"poster");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG");
        values.put(MediaStore.Images.Media.TITLE, String.valueOf(System.currentTimeMillis()).substring(0, 11) + ".jpg");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //Android 10以上
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/" + context.getPackageName());
        } else {
            values.put(MediaStore.Images.Media.DATA, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath());
        }
        Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = MyApplication.get(context).getContentResolver();
        Uri insertUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            insertUri = resolver.insert(external, values);
        } else {
            insertUri = resolver.insert(external, new ContentValues());
        }
        OutputStream os = null;

        if (insertUri != null) {
            try {
                os = resolver.openOutputStream(insertUri);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
                bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
