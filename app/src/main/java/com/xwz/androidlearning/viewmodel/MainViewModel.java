package com.xwz.androidlearning.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;

import com.google.gson.Gson;
import com.xwz.androidlearning.model.MainModel;
import com.xwz.androidlearning.util.GetJsonDataUtil;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/*
 *  首页ViewModel
 *  Observable 是被观察目标（subject）对象，当它的数据发生变化，
 *  调用setChanged();notifyObserves();
 */
public class MainViewModel extends Observable {

    public ObservableInt mainRcv;
    public ObservableInt peopleLabel;

    private List<MainModel> mainModelList;
    private Context context;

    public MainViewModel(@NonNull Context context) {
        this.context = context;
        this.mainModelList = new ArrayList<>();
        mainRcv = new ObservableInt(View.VISIBLE);
        peopleLabel = new ObservableInt(View.VISIBLE);
        initJsonData();
    }
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(context, "maindata.json");//获取assets目录下的json文件数据
        ArrayList<MainModel> jsonBean = parseData(JsonData);//用Gson转成实体
        changeMainDataSet(jsonBean);
    }


    public ArrayList<MainModel> parseData(String result) {//Gson 解析
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
}
