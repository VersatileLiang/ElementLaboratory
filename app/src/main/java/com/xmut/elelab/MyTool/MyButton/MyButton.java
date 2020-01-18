package com.xmut.elelab.MyTool.MyButton;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/1/10 16:26
 */
public class MyButton extends AbstractDragFloatActionButton {

    private int custom_button;

    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return custom_button;//拿到你自己定义的悬浮布局
    }

    public void setCustom_button(int custom_button){
        this.custom_button = custom_button;
    }

    @Override
    public void renderView(View view) {
        //初始化那些布局
    }
}

