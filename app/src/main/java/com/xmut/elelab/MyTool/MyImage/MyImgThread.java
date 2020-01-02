package com.xmut.elelab.MyTool.MyImage;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

import com.xmut.elelab.MyTool.MyThread.MyThread;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 网络图片加载线程类
 * @author hasee
 * time: 2020年1月1日14:21:39
 */
public class MyImgThread extends MyThread {

    private String url;

    /**
     * 传入初始参数
     * @param url 图片网址
     */
    public MyImgThread(String url){
        this.url = url;
    }
    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            drawable = Drawable.createFromStream(in, "background.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}