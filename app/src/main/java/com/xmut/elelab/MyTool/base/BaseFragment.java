package com.xmut.elelab.MyTool.base;

import android.os.Bundle;
import android.view.View;

import com.cy.translucentparent.StatusNavUtils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/4/19 1:28
 */
public class BaseFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        StatusNavUtils.setStatusBarColor(this,0x00000000); //状态栏全透明
        StatusNavUtils.setStatusBarColor(Objects.requireNonNull(getActivity()),0x33000000); //状态栏半透明

//        StatusNavUtils.setNavigationBarColor(this,0x00000000); //导航栏全透明
        StatusNavUtils.setNavigationBarColor(Objects.requireNonNull(getActivity()),0x33000000); //导航栏半透明
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        StatusNavUtils.setStatusBarColor(this,0x00000000); //状态栏全透明
        StatusNavUtils.setStatusBarColor(Objects.requireNonNull(getActivity()),0x33000000); //状态栏半透明

//        StatusNavUtils.setNavigationBarColor(this,0x00000000); //导航栏全透明
        StatusNavUtils.setNavigationBarColor(Objects.requireNonNull(getActivity()),0x33000000); //导航栏半透明
    }
}
