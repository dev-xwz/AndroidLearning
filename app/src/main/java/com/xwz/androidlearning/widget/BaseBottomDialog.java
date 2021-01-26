package com.xwz.androidlearning.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class BaseBottomDialog extends Dialog {

    public BaseBottomDialog(Context context,int theme, int res) {
        super(context,theme);
        setContentView(res);
        setCancelable(false);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);//设置显示在底部
        WindowManager windowManager=getWindow().getWindowManager();
        Display display= windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams=getWindow().getAttributes();
        layoutParams.width=display.getWidth();//设置Dialog的宽度为屏幕宽度
        getWindow().setAttributes(layoutParams);
    }
}
