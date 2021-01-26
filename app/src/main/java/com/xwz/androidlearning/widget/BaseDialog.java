package com.xwz.androidlearning.widget;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

/**
 * @author 柯拉松
 */
public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context, int theme,int res) {
        super(context,theme);
        setContentView(res);
        setCancelable(false);
    }
}
