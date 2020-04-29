package com.xmut.elelab.MyTool.TouchMoveView;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.xmut.elelab.R;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/4/20 0:30
 */

public class TouchMoveView extends RelativeLayout {
    private static final String TAG = "TouchMoveView";
    private View mTargetView;
    private View mRedView, mBlueView, mGreenView, mYellowView;
    private RectF mRedRect, mBlueRect, mGreenRect, mYellowRect, mTargetRect;

    private View mParentView;

    public TouchMoveView(Context context) {
        this(context, null);
    }

    public TouchMoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private View mTouchedView;
    private float mInitX;
    private float mInitY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initRect();
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mTouchedView = getTouchedView((int) x, (int) y);
                if (null != mTouchedView) {
                    mInitX = mTouchedView.getX();
                    mInitY = mTouchedView.getY();
                }
                Log.e(TAG, "mTouchedView:" + mTouchedView);
                return true;
            case MotionEvent.ACTION_MOVE:
                if (null != mTouchedView) {
                    float moveX = x - mTouchedView.getWidth() / 2;
                    float moveY = y - mTouchedView.getHeight() / 2;
                    Log.e(TAG, "moveX:" + moveX + "  moveY:" + moveY);
                    mTouchedView.setX(moveX);
                    mTouchedView.setY(moveY);

                    if (isTwoViewClose(mTouchedView, mTargetView)) {
                        int colorRes = getColorRes(mTouchedView);
                        mTargetView.setBackgroundColor(getResources().getColor(colorRes));
                    } else {
                        mTargetView.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_10_pure_black));
                    }
                }
                return true;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (null != mTouchedView) {
                    mTouchedView.setX(mInitX);
                    mTouchedView.setY(mInitY);
                }
                mTargetView.setBackgroundColor(getResources().getColor(R.color.qmui_config_color_10_pure_black));
                return true;
            default:
                return false;
        }
    }

    private int getColorRes(View view) {
        if (null == view) {
            return R.color.qmui_config_color_10_pure_black;
        }
        int id = view.getId();
        switch (id) {
            default:
                return R.color.qmui_config_color_10_pure_black;
        }
    }

    private View getTouchedView(int x, int y) {
        Log.e(TAG, "x:" + x + " y:" + y);
        Log.e(TAG, "mRedRect" + mRedRect);
        Log.e(TAG, "mBlueRect" + mBlueRect);
        Log.e(TAG, "mGreenRect" + mGreenRect);
        Log.e(TAG, "mYellowRect" + mYellowRect);
        if (mRedRect.contains(x, y)) {
            return mRedView;
        }

        if (mBlueRect.contains(x, y)) {
            return mBlueView;
        }

        if (mGreenRect.contains(x, y)) {
            return mGreenView;
        }

        if (mYellowRect.contains(x, y)) {
            return mYellowView;
        }
        return null;

    }

    private void initRect() {
        if (null == mRedRect) {
            mRedRect = getRect(mRedView);
        }

        if (null == mBlueRect) {
            mBlueRect = getRect(mBlueView);
        }
        if (null == mGreenRect) {
            mGreenRect = getRect(mGreenView);
        }
        if (null == mYellowRect) {
            mYellowRect = getRect(mYellowView);
        }
        if (null == mTargetRect) {
            mTargetRect = getNormalRect(mTargetView);
        }
    }

    private RectF getNormalRect(View view) {
        return new RectF(view.getX(), view.getY(), view.getX() + view.getWidth(), view.getY() + view.getHeight());
    }

    private RectF getRect(View view) {
        RectF rectf = new RectF(view.getX(), view.getY() + mParentView.getY(), view.getX() + view.getWidth(), view.getY() + mParentView.getY() + view.getHeight());
        return rectf;
    }

    /**
     * 判断两个View是否相交
     *
     * @param src
     * @param target
     * @return
     */
    private boolean isTwoViewClose(View src, View target) {
        RectF rectSrc = getNormalRect(src);
        RectF rectTar = getNormalRect(target);
        return rectSrc.intersect(rectTar);
    }
}