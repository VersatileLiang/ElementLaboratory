package com.xmut.elelab.MyTool.MyThread;

import android.graphics.drawable.Drawable;

/**
 * 我的线程类
 * 简单实用线程
 */

public class MyThread extends Thread {
    /**
     *
     */
    protected Drawable drawable;

    @Override
    public void run() {

    }

    public Drawable getResult() {
        return this.drawable;
    }
}