package com.xwz.androidlearning.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.xwz.androidlearning.R;
import com.xwz.androidlearning.databinding.ActivityMainBinding;
import com.xwz.androidlearning.view.adapter.MainAdapter;
import com.xwz.androidlearning.viewmodel.MainViewModel;

import java.util.Observable;
import java.util.Observer;

/*
 *  首页
 *  Observer观察者角色 唯一方法update，被观察者需要调用addObserver()方法，
 * 添加它的观察者列表
 */
public class MainActivity extends AppCompatActivity implements Observer {

    private MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        setupListView(binding.rcvMain);
        setupObserver(mainViewModel);
    }

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainViewModel = new MainViewModel(this);
        binding.setMainViewModel(mainViewModel);
    }

    private void setupListView(RecyclerView rcvMain) {
        MainAdapter adapter = new MainAdapter();
        adapter.setMainModelList(mainViewModel.getMainModelList());
        rcvMain.setAdapter(adapter);
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
}
