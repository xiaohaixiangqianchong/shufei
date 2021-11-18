package com.ubestkid.kidrhymes.utils;

import android.content.Context;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity的fragment管理类
 */
public class MainBottomMenuManager implements RadioGroup.OnCheckedChangeListener {
    FragmentManager fm;
    RadioGroup rgs;
    List<Fragment> fragments = new ArrayList<>();
    private int containerId;
    private Context context;
    private OnBottomTabSelectListener onBottomTabSelectListener;

    public MainBottomMenuManager(Context context, List<Fragment> fragments, RadioGroup rgs, FragmentManager fm, int containerId) {
        this.context = context;
        this.rgs = rgs;
        this.fm = fm;
        this.fragments = fragments;
        this.containerId = containerId;
        rgs.setOnCheckedChangeListener(this);
        setCheckPosition(0);
        getFragmentTransaction().add(containerId, fragments.get(0)).commit();
    }

    public MainBottomMenuManager(Context context, List<Fragment> fragments, RadioGroup rgs, FragmentManager fm, int containerId, OnBottomTabSelectListener onBottomTabSelectListener) {
        this(context, fragments, rgs, fm, containerId);
        this.onBottomTabSelectListener = onBottomTabSelectListener;
    }

    public MainBottomMenuManager addFragment(Fragment fragment) {
        fragments.add(fragment);
        return this;
    }

    public void setCheckPosition(int position) {
        RadioButton rb = (RadioButton) rgs.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //获得容器控件的所有子类
        for (int i = 0; i < group.getChildCount(); i++) {
            Fragment fragment = fragments.get(i);
            //如果被选中显示
            if (group.getChildAt(i).getId() == checkedId) {
                //fragment是否被添加
                if (!fragment.isAdded()) {
                    getFragmentTransaction().add(containerId, fragment).commit();
                }
                if (fragment.isHidden()) {
                    getFragmentTransaction().show(fragment).commit();
                }
                if (onBottomTabSelectListener != null) {
                    onBottomTabSelectListener.onBottomTabSelectListener(group, checkedId, i);
                }
            } else {
                //隐藏
                getFragmentTransaction().hide(fragment).commit();
            }
        }
    }

    private FragmentTransaction getFragmentTransaction() {
        FragmentTransaction ft = fm.beginTransaction();
        return ft;
    }

    public interface OnBottomTabSelectListener {
        void onBottomTabSelectListener(RadioGroup group, int checkedId, int index);
    }

}
