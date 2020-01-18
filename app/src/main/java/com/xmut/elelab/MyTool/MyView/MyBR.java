package com.xmut.elelab.MyTool.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/1/11 0:17
 */
public class MyBR extends RelativeLayout {
    // 属性变量
    private int parentHeight;//悬浮的父布局高度
    private int parentWidth;

    private int lastX;
    private int lastY;

    private boolean isDrag;

    public MyBR(Context context) {
        this(context, null);
    }

    public MyBR(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyBR(Context context, AttributeSet attrs, int defStyleAttr) {
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
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                setPressed(true);//默认是点击事件
                isDrag=false;//默认是非拖动而是点击事件
                getParent().requestDisallowInterceptTouchEvent(true);//父布局不要拦截子布局的监听
                lastX=rawX;
                lastY=rawY;
                ViewGroup parent;
                if(getParent()!=null){
                    parent= (ViewGroup) getParent();
                    parentHeight=parent.getHeight();
                    parentWidth=parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                isDrag = (parentHeight > 0 && parentWidth > 0);//只有父布局存在你才可以拖动
                if(!isDrag) break;

                int dx=rawX-lastX;
                dx/=ZoomView.scale;
                int dy=rawY-lastY;
                dy/=ZoomView.scale;
                //这里修复一些华为手机无法触发点击事件
                int distance= (int) Math.sqrt(dx*dx+dy*dy);
                isDrag = distance>0;//只有位移大于0说明拖动了
                if(!isDrag) break;

                float x=getX()+dx;
                float y=getY()+dy;
                //检测是否到达边缘 左上右下
                x=x<0?0:x>parentWidth-getWidth()?parentWidth-getWidth():x;
                y=y<0?0:y>parentHeight-getHeight()?parentHeight-getHeight():y;
                setX(x);
                setY(y);
                lastX=rawX;
                lastY=rawY;
                break;
            case MotionEvent.ACTION_UP:
                setPressed(!isDrag);
                break;
            case MotionEvent.ACTION_POINTER_UP:
//                moveType = 0;
        }
        //如果不是拖拽，那么就不消费这个事件，以免影响点击事件的处理
        //拖拽事件要自己消费
        return isDrag || super.onTouchEvent(event);
    }
}
