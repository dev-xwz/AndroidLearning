package com.xwz.androidlearning.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.databinding.BaseObservable;

import com.xwz.androidlearning.model.MainModel;

public class ItemMainViewModel extends BaseObservable {

    private final Context context;
    private MainModel mainModel;

    public ItemMainViewModel(Context context, MainModel mainModel) {
        this.context = context;
        this.mainModel = mainModel;
    }

    public String getMainName() {
        return mainModel.getName();
    }

    public String getMainValue() {
        return mainModel.getValue();
    }

    public void onItemClick(View view) {
//        context.startActivity(PeopleDetailActivity.launchDetail(view.getContext(), people));
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        notifyChange();
    }
}
