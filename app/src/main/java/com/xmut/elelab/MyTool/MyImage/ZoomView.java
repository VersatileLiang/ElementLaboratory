package com.xmut.elelab.MyTool.MyImage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * IDEA 2019.1.3
 * 界面单指拖动双指缩放类
 * @author kaisong liang
 * @version 1.0
 * @date 2020/1/7 15:57
 */
public class ZoomView extends RelativeLayout {
    // 属性变量
    private float translationX; // 移动X
    private float translationY; // 移动Y
    private float scale = 1; // 伸缩比例
    private float rotation; // 旋转角度
    private int widthZoom; //界面宽度
    private int heightZoom; //界面高度
    private int sign = 0; //标记这个宽高是不是第一次赋值

    // 移动过程中临时变量
    private float actionX;
    private float actionY;
    private float spacing;
    private float degree;
    private int moveType; // 0=未选择，1=拖动，2=缩放

    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (sign == 0){
            widthZoom = getMeasuredWidth();
            heightZoom = getMeasuredHeight();
            sign = 1;
        }
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                moveType = 1;
                actionX = event.getRawX();
                actionY = event.getRawY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                moveType = 2;
                spacing = getSpacing(event);
                degree = getDegree(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveType == 1) {
                    translationX = translationX + event.getRawX() - actionX;
                    translationY = translationY + event.getRawY() - actionY;
                    setTranslationX(translationX);
                    setTranslationY(translationY);
//                    Log.e(TAG, "onTouchEvent: "+translationX +"."+translationY);
                    actionX = event.getRawX();
                    actionY = event.getRawY();
                } else if (moveType == 2) {
                    scale = scale * getSpacing(event) / spacing;
                    setScaleX(scale);
                    setScaleY(scale);
                    rotation = rotation + getDegree(event) - degree;
                    if (rotation > 360) {
                        rotation = rotation - 360;
                    }
                    if (rotation < -360) {
                        rotation = rotation + 360;
                    }
//                    setRotation(rotation); //旋转功能
                }
                break;
            case MotionEvent.ACTION_UP:
                if (scale <= 1){ //如果界面不够整个屏幕
                    scale = 1;
                    translationX = 0;
                    translationY = 0;
                    setScaleX(scale);
                    setScaleY(scale);
                    setTranslationX(translationX);
                    setTranslationY(translationY);
                } else if(scale >= 3){ //如果界面放的太大
                    scale = 3;
                    setScaleX(scale);
                    setScaleY(scale);
                }
                //界面放大但是超出去了露出背景色，做出相应的修正
                if (translationX > -1*(1-scale)*widthZoom/2){
                    translationX = -1*(1-scale)*widthZoom/2;
                    setScaleX(scale);
                    setTranslationX(translationX);
                }
                if (translationY > -1*(1-scale)*heightZoom/2){
                    translationY = -1*(1-scale)*heightZoom/2;
                    setScaleY(scale);
                    setTranslationY(translationY);
                }
                if (translationX < (1-scale)*widthZoom/2){
                    translationX = (1-scale)*widthZoom/2;
                    setScaleX(scale);
                    setTranslationX(translationX);
                }
                if (translationY < (1-scale)*heightZoom/2){
                    translationY = (1-scale)*heightZoom/2;
                    setScaleY(scale);
                    setTranslationY(translationY);
                }
            case MotionEvent.ACTION_POINTER_UP:
                moveType = 0;
        }
        return super.onTouchEvent(event);
    }

    // 触碰两点间距离
    private float getSpacing(MotionEvent event) {
        //通过三角函数得到两点间的距离
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    // 取旋转角度
    private float getDegree(MotionEvent event) {
        //得到两个手指间的旋转角度
        double delta_x = event.getX(0) - event.getX(1);
        double delta_y = event.getY(0) - event.getY(1);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}