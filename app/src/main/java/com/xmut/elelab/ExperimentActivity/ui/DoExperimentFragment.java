package com.xmut.elelab.ExperimentActivity.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.MyView.ZoomView;
import com.xmut.elelab.MyTool.SystemUtil.SystemUtil;
import com.xmut.elelab.R;

import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoExperimentFragment extends QMUIFragment {


    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.do_experiment_fragment, null);
        ButterKnife.bind(this, root);

//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.SOFT_INPUT_IS_FORWARD_NAVIGATION);

        //这里的view为fragment，minSdkVersion必须大于等于18
        root.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onWindowFocusChanged(final boolean hasFocus) {
                // do your stuff here
//                View decorView = getActivity().getWindow().getDecorView();
//                decorView.setSystemUiVisibility(
//                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//                Log.e(getTag(), "onWindowFocusChanged: "+SystemUtil.getDeviceBrand()+" " + SystemUtil.getSystemSDK());
//                if ((SystemUtil.getDeviceBrand().equals("HUAWEI")||
//                        SystemUtil.getDeviceBrand().equals("Huawei")||
//                        SystemUtil.getDeviceBrand().equals("huawei")||
//                        SystemUtil.getDeviceBrand().equals("HONOR")||
//                        SystemUtil.getDeviceBrand().equals("Honer")||
//                        SystemUtil.getDeviceBrand().equals("honer"))&&
//                        SystemUtil.getSystemSDK()>=19){
//                    LinearLayout linearLayout = root.findViewById(R.id.linear_main);
//                    RelativeLayout relativeLayout = root.findViewById(R.id.zoomView);
//                    ConstraintLayout constraintLayout = root.findViewById(R.id.do_experiment_fragment);
//                    //measure方法的参数值都设为0即可
//                    linearLayout.measure(0,0);
//                    constraintLayout.measure(0,0);
//                    //获取组件宽度
//                    int width = constraintLayout.getWidth();
//                    //获取组件高度
//                    int height = constraintLayout.getHeight();
//                    //重新设置宽度高度
////                    Log.e(getTag(), "onWindowFocusChanged: " + getNavigationBarHeight());
//                    linearLayout.setLayoutParams(
//                            new ConstraintLayout.LayoutParams(
//                                    (width + getNavigationBarHeight()), height
//                            )
//                    );
////                    relativeLayout.getWidth();
////                    relativeLayout.getHeight();
////                    relativeLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
////                            (width + getNavigationBarHeight()), height
////                    ));
//                    constraintLayout.setLayoutParams(
//                            new ConstraintLayout.LayoutParams(
//                                    (width + getNavigationBarHeight()), height
//                            )
//                    );
//                    linearLayout.invalidate();
//                    relativeLayout.invalidate();
//                    constraintLayout.invalidate();
////                    linearLayout.releasePointerCapture();
////                    linearLayout.setTranslationX(width + getNavigationBarHeight());
////                    linearLayout.setTranslationY(height);
//                }
            }
        });
        return root;
    }

    @OnClick({R.id.beaker})
    void onClickBeaker(View v) {
        QMUIPopups.quickAction(getContext(),
                QMUIDisplayHelper.dp2px(getContext(), 56),
                QMUIDisplayHelper.dp2px(getContext(), 56))
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_copy).text("清空").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "清空成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_line).text("标记").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "标记成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_share).text("拿起").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "拿起成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .show(v);
    }

    @OnClick({R.id.start_element_move, R.id.movable_experimental_equipment_1})
    void onClickTest(View v) {
        QMUIPopups.quickAction(getContext(),
                QMUIDisplayHelper.dp2px(getContext(), 56),
                QMUIDisplayHelper.dp2px(getContext(), 56))
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_copy).text("旋转").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "旋转成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_line).text("标记").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "标记成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.icon_quick_action_share).text("拿起").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                Toast.makeText(getContext(), "拿起成功", Toast.LENGTH_SHORT).show();
                            }
                        }
                ))
                .show(v);
    }
}
