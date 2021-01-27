package com.xwz.androidlearning.injection.module;

import android.content.Context;

import androidx.fragment.app.Fragment;

import com.xwz.androidlearning.injection.qualifier.FragmentContext;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @FragmentContext
    Context providesContext() {
        return mFragment.getContext();
    }
}
