package com.xmut.elelab.ExperimentActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment;
import com.qmuiteam.qmui.arch.annotation.FirstFragments;
import com.qmuiteam.qmui.arch.annotation.LatestVisitRecord;
import com.xmut.elelab.ExperimentActivity.ui.DoExperimentFragment;
import com.xmut.elelab.MyTool.base.BaseQMUIFragmentActivity;
import com.xmut.elelab.R;

/**
 * 做实验的界面
 * 核心！！
 * 主体实验操作逻辑基本上都在这里面
 */
@FirstFragments(
        value = {
                DoExperimentFragment.class,
        })
@DefaultFirstFragment(DoExperimentFragment.class)
@LatestVisitRecord
public class DoExperimentActivity extends BaseQMUIFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.layout.do_experiment_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_do_experiment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);//横屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        QMUISwipeBackActivityManager.init(this.getApplication());
        super.onCreate(savedInstanceState);
    }

    /**
     * 界面控制
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//            View decorView = getWindow().getDecorView();
//            decorView.setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//        }
    }
}
