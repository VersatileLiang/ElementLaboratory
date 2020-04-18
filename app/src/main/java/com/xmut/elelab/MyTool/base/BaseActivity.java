/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xmut.elelab.MyTool.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.cy.translucentparent.StatusNavUtils;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.xmut.elelab.MyTool.AppContext.AppContext.getContext;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 在 Application 里初始化
        // 然后就可以使用 arch 库提供的 QMUIActivity 来作为基础类构建自己的界面了。
        QMUISwipeBackActivityManager.init(getApplication());

//        StatusNavUtils.setStatusBarColor(this,0x00000000); //状态栏全透明
        StatusNavUtils.setStatusBarColor(this,0x33000000); //状态栏半透明

//        StatusNavUtils.setNavigationBarColor(this,0x00000000); //导航栏全透明
        StatusNavUtils.setNavigationBarColor(this,0x33000000); //导航栏半透明
    }
    public void startAppcompatActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }
}
