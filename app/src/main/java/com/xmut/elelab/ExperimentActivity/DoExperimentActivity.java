package com.xmut.elelab.ExperimentActivity;

import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager;
import com.qmuiteam.qmui.arch.annotation.DefaultFirstFragment;
import com.qmuiteam.qmui.arch.annotation.FirstFragments;
import com.qmuiteam.qmui.arch.annotation.LatestVisitRecord;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xmut.elelab.ExperimentActivity.ui.DoExperimentFragment;
import com.xmut.elelab.MyTool.MyView.MyBR;
import com.xmut.elelab.R;

import butterknife.OnClick;

import static com.xmut.elelab.MyTool.AppContext.AppContext.getContext;

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
public class DoExperimentActivity extends QMUIFragmentActivity {

    @Override
    protected int getContextViewId() {
        return R.layout.do_experiment_fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_do_experiment);
        QMUISwipeBackActivityManager.init(this.getApplication());
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
//        Button button = findViewById(R.id.experiment_equipment);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(DoExperimentActivity.this,"开始做实验!!！",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.do_experiment, DoExperimentFragment.newInstance())
//                    .commitNow();
//        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
