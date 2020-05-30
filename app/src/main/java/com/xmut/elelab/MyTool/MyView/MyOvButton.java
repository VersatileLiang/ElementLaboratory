package com.xmut.elelab.MyTool.MyView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * IDEA 2019.1.3
 * 可以旋转的可拖动按钮
 * 用于试管等液体操作的实验器材
 * @author kaisong liang
 * @version 1.0
 * @date 2020/3/21 1:30
 */
public class MyOvButton extends MyRelativeButton {

    protected float rotation; // 旋转角度
    protected int moveType; // 0=未选择，1=拖动，2=旋转
    protected float degree;

    public MyOvButton(Context context) {
        super(context);
    }

    public MyOvButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyOvButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
                moveType = 1;
                setPressed(true);//默认是点击事件
                isDrag=false;//默认是非拖动而是点击事件
                getParent().requestDisallowInterceptTouchEvent(true);//父布局不要拦截子布局的监听
                lastX=rawX;
                lastY=rawY;
                ViewGroup parent;
                if(getParent()!=null){
                    parent= (ViewGroup) getParent();
                    parentHeight=parent.getHeight(); //获取父布局高度
                    parentWidth=parent.getWidth();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                moveType = 2;
                degree = getDegree(event);
                break;
            case MotionEvent.ACTION_MOVE:
                if (moveType == 1) {
                    isDrag = (parentHeight > 0 && parentWidth > 0);//只有父布局存在你才可以拖动
                    if(!isDrag){ //父布局不存在
                        break;
                    }else {
                        int dx=rawX-lastX;
                        dx/=ZoomView.scale;
                        int dy=rawY-lastY;
                        dy/=ZoomView.scale;
                        //这里修复一些华为手机无法触发点击事件
                        int distance= (int) Math.sqrt(dx*dx+dy*dy);
                        isDrag = distance>0;//只有位移大于0说明拖动了
                        if(!isDrag){ //位移小于0
                            break;
                        }else { //确定是拖动
                            float x=getX()+dx;
                            float y=getY()+dy;
                            //检测是否到达边缘 左上右下。边缘检测
                            //修正边缘
                            x=x<0?0:x>parentWidth-getWidth()?parentWidth-getWidth():x;
                            y=y<0?0:y>parentHeight-getHeight()?parentHeight-getHeight():y;
                            setX(x);
                            setY(y);
                            lastX=rawX;
                            lastY=rawY;

                            time = System.currentTimeMillis();
                        }
                    }
                    break;
                } else if (moveType == 2) {
                    rotation = rotation + getDegree(event) - degree;
                    if (rotation > 360) {
                        rotation = rotation - 360;
                    }
                    if (rotation < -360) {
                        rotation = rotation + 360;
                    }
                    setRotation(rotation); //旋转功能
                    break;
                }
            case MotionEvent.ACTION_UP:
                //移动后会发生小概率的误触点击事件，影响操作
                //移动后点击误触的判断，小于500毫秒的全部判断为误触
                if (System.currentTimeMillis() - time < 500){
                    setPressed(false);
                }else {
                    setPressed(!isDrag);
                }
                moveType = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                moveType = 0;
        }
        //如果不是拖拽，那么就不消费这个事件，以免影响点击事件的处理
        //拖拽事件要自己消费
        return isDrag || super.onTouchEvent(event);
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
