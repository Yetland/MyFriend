package com.yetland.myfriend.core.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yetland.myfriend.core.AppApplication;
import com.yetland.myfriend.core.dagger2.component.AppComponent;

/**
 * Created by yeliang on 2016/4/21.
 */
public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActivityComponent(AppApplication.get(getActivity()).getAppComponent());
    }

    protected abstract  void setupActivityComponent(AppComponent appComponent);
}
