package com.xmut.elelab.MyTool.MyThread;

import android.graphics.drawable.Drawable;

import java.util.Map;

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