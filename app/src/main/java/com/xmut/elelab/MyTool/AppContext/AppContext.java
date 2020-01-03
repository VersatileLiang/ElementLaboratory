package com.xmut.elelab.MyTool.AppContext;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

/**
 * 全局调用context的工具类
 * 注意要修改AndroidManifest.xml文件，告知系统程序启动时需要初始化AppContext类，而不是默认的Application类。
 * 之后不管你想在项目的任何地方使用Context，只需要调用一下AppContext.getContext()就可以了。
 * @author kaisong liang
 * @date 2020/1/3 15:42
 */
@SuppressLint("Registered")
public class AppContext extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext()
    {
        return context;
    }
}

