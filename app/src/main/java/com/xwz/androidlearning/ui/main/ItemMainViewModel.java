package com.xwz.androidlearning.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.databinding.BaseObservable;

import com.bumptech.glide.Glide;
import com.xwz.androidlearning.R;
import com.xwz.androidlearning.widget.custom_view.CircleImageView;
import com.xwz.androidlearning.widget.BaseBottomDialog;
import com.xwz.androidlearning.widget.BaseDialog;

public class ItemMainViewModel extends BaseObservable {

    private final Context context;
    private MainModel mainModel;
    private MainViewModel mainViewModel;

    public ItemMainViewModel(Context context, MainModel mainModel) {
        this.context = context;
        this.mainModel = mainModel;
        this.mainViewModel = new MainViewModel(context);
    }

    public String getMainName() {
        return mainModel.getName();
    }

    public int getMainValue() {
        return mainModel.getValue();
    }

    public void onItemClick(View view) {
        switch (getMainValue()){
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                showShareDialog();
                break;
            case 6:
                break;
            default:
                break;
        }
    }

    public void setMainModel(MainModel mainModel) {
        this.mainModel = mainModel;
        notifyChange();
    }

    private void showShareDialog() {
        final BaseBottomDialog baseBottomDialog = new BaseBottomDialog(context, R.style.BaseDialog, R.layout.dialog_share);
        LinearLayout mLlPoster = baseBottomDialog.findViewById(R.id.ll_poster);
        TextView mTvCancel = baseBottomDialog.findViewById(R.id.tv_cancel);
        mLlPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseBottomDialog.dismiss();
                showPosterDialog();
            }
        });
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseBottomDialog.cancel();
            }
        });
        baseBottomDialog.show();
    }

    private void showPosterDialog(){
        final BaseDialog baseDialog = new BaseDialog(context, R.style.BaseDialog, R.layout.dialog_poster);
        final LinearLayout mLlPoster = baseDialog.findViewById(R.id.ll_poster);
        ImageView mIvGoods = baseDialog.findViewById(R.id.iv_goods);
        CircleImageView mIvUser = baseDialog.findViewById(R.id.iv_user);
        ImageView mIvCode = baseDialog.findViewById(R.id.iv_code);
        Glide.with(context).load("https://gd4.alicdn.com/imgextra/i2/203152190/O1CN01akJmPs1S33tcEacSr_!!203152190.jpg").into(mIvGoods);
        Glide.with(context).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F202005%2F25%2F20200525131219_ntnpg.thumb.400_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614161732&t=fafc3e7ce5daa03f5e02f6d747436d53").into(mIvUser);
        Glide.with(context).load("data:image/png;base64,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").into(mIvCode);
        TextView mTvSave = baseDialog.findViewById(R.id.tv_save);
        ImageView mIvClose = baseDialog.findViewById(R.id.iv_close);
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewModel.savePoster(mLlPoster);
            }
        });
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseDialog.cancel();
            }
        });
        baseDialog.show();
    }


}
