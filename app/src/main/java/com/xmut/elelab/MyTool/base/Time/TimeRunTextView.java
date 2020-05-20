package com.xmut.elelab.MyTool.base.Time;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;

import static com.youth.banner.Banner.TAG;

/**
 * IDEA 2019.1.3
 *
 * @author kaisong liang
 * @version 1.0
 * @date 2020/5/20 4:05
 */

@SuppressLint("AppCompatCustomView")
public class TimeRunTextView extends TextView {
    private long mHour;//小时
    private long mMin;//分钟
    private long mSecond;//秒
    private OnTimeViewListener timeViewListener;//时间结束
    private String MODE = "1";//时间展示模式
    private Timer timer = null;
    private TimerTask timerTask = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String getResult = (String) msg.obj;
            TimeRunTextView.this.setText(getResult);
        }

    };


    public TimeRunTextView(Context context) {
        super(context);
    }

    public TimeRunTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeRunTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTimeViewListener(OnTimeViewListener timeViewListener) {
        this.timeViewListener = timeViewListener;
    }

    public void startTime(long timeCount, String mode) {
        initTime();//先清空时间的工具类
        if (!TextUtils.isEmpty(mode)) {
            this.MODE = mode;
        }
        startTime(timeCount);

    }

    public void startTime(long timeCount) {
        initTime();//先清空时间的工具类
        mHour = timeCount / (60 * 60);  //  对3600 取整  就是小时
        mMin = (timeCount % (60 * 60)) / 60;//  对3600 取余除以60 就是多出的分
        mSecond = timeCount % 60; //  对60 取余  就是多出的秒
        if (timer == null) {
            timer = new Timer();
        }
        if (timerTask == null) {
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    if ((mHour + mMin + mSecond) > 0) {
                        ComputeTime();//计算时分秒
                    } else {
                        stopTime();
                    }

                }
            };
        }
        timer.schedule(timerTask, 0, 1000);
        if (timeViewListener != null) {
            timeViewListener.onTimeStart();
        }
    }

    public void stopTime() {
        try {
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        } catch (Exception e) {

        }
        if (timeViewListener != null) {
            timeViewListener.onTimeEnd();
        }
    }


    public void initTime() {
        try {
            if (timerTask != null) {
                timerTask.cancel();
                timerTask = null;
            }
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        } catch (Exception e) {
            Log.e(TAG, "initTime: error");
        }
    }


    private void ComputeTime() {
        if (mSecond == 0) {
            //  秒为0  判断min 是否为0
            if (mMin == 0) {
                //  分为0 判断hour
                if (mHour == 0) {
                    Log.e("mcy--", "时间结束"); //  秒 分 时  都为0  倒计时结束
                } else {
                    //  此处为hour 不为0 秒 分 为0  所以 hour-- 秒 分 为 59
                    mHour--;
                    mMin = 59;
                    mSecond = 59;
                }
            } else {
                //  分不为0  所以不用修改 hour  分减一即可 秒 变为 59
                mMin--;
                mSecond = 59;
            }
        } else {
            //  秒不为0  秒减一 分 和时  不变 继续循环
            mSecond--;
        }
        String strTime = "";
        switch (MODE) {
            case "1":
                strTime = mHour + "时" + mMin + "分" + mSecond + "秒";
                break;
            case "2":
                if (mMin < 10){
                    if (mSecond < 10){
                        strTime = "剩余时间：" + "0" + mMin + ":" + "0" + mSecond;
                    }else {
                        strTime = "剩余时间：" + "0" + mMin + ":" + mSecond;
                    }
                }else {
                    if (mSecond < 10){
                        strTime = "剩余时间：" + mMin + ":" + "0" + mSecond;
                    }else {
                        strTime = "剩余时间：" + mMin + ":" + mSecond;
                    }
                }
                break;
            default:
                strTime = mHour + "时" + mMin + "分" + mSecond + "秒";
                break;
        }
        Message msg = Message.obtain();
        msg.obj = strTime;   //从这里把你想传递的数据放进去就行了
        handler.sendMessage(msg);
    }

    public interface OnTimeViewListener {
        void onTimeStart();

        void onTimeEnd();
    }
}