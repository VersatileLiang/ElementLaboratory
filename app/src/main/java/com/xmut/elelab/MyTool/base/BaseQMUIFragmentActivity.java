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

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.SystemUtil.SystemUtil;

import java.lang.reflect.Method;

import androidx.annotation.Nullable;

/**
 * Created by cgspine on 2018/1/7.
 */

public abstract class BaseQMUIFragmentActivity extends QMUIFragmentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        // 在 Application 里初始化
        // 然后就可以使用 arch 库提供的 QMUIFragmentActivity 来作为基础类构建自己的界面了。
        QMUISwipeBackActivityManager.init(getApplication());
        super.onCreate(savedInstanceState, persistentState);
    }

    //判断NavigationBar的最终确认，true表示有
    public static boolean checkNavigationBar(Context context){
        boolean result;
        // 如果是华为或者荣耀
        if ((SystemUtil.getDeviceBrand().equals("HUAWEI")||
                        SystemUtil.getDeviceBrand().equals("Huawei")||
                        SystemUtil.getDeviceBrand().equals("huawei")||
                        SystemUtil.getDeviceBrand().equals("HONOR")||
                        SystemUtil.getDeviceBrand().equals("Honer")||
                        SystemUtil.getDeviceBrand().equals("honer"))&&
                        SystemUtil.getSystemSDK()>=19){
            result = checkHuaWeiDeviceHasNavigationBar(context);
        }else {
            result = checkDeviceHasNavigationBar(context);
        }

        // 如果存在NavigationBar，判断显示
//        if (result){
//            result = isNavigationBarShow(); //Activity 暂时有问题，等待以后解决
//        }
        return result;
    }

    //获取是否存在NavigationBar
    public static boolean checkDeviceHasNavigationBar(Context context) {
        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get(context)
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            // 做任何你需要做的,这个设备有一个导航栏
            int a;
            a = 2;
            int b;
            b = a;
            if (b == a){
                hasBackKey = true;
            }
            //随便写的
            return true;
        }
        return false;
    }

    //获取是否存在NavigationBar
    public static boolean checkHuaWeiDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        try {
            Resources rs = context.getResources();
            int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id);
            }
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            Log.e("huaWei error", "checkHuaWeiDeviceHasNavigationBar: " + e);
        }
        return hasNavigationBar;
    }

    //NavigationBar状态是否是显示
    public static boolean isNavigationBarShow() {
        Context context = AppContext.getContext();
        Activity mContext = (Activity) context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Display display = mContext.getWindowManager().getDefaultDisplay();
            Point size = new Point();
            Point realSize = new Point();
            display.getSize(size);
            display.getRealSize(realSize);
            return realSize.y != size.y;
        } else {
            boolean menu = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean back = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
            if (menu || back) {
                return false;
            } else {
                return true;
            }
        }
    }

    //获取NavigationBar高度（不论有没有）
    public static int getNavigationBarHeight() {
        Context context = AppContext.getContext();
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
//        Log.e("dbw", "Navi height:" + height);
        return height;
    }

    //获取NavigationBar的最终高度
    public static int getNavigationBarFinalHeight() {
        int height = 0;
        if (checkNavigationBar(AppContext.getContext())){
            height = getNavigationBarHeight();
        }
        return height;
    }
}
