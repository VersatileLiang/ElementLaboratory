package com.xmut.elelab.ExperimentActivity.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xmut.elelab.ExperimentActivity.ExperimentActivity;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.MyView.ZoomView;
import com.xmut.elelab.MyTool.base.BaseQMUIFragment;
import com.xmut.elelab.MyTool.base.Time.TimeRunTextView;
import com.xmut.elelab.R;
import com.xmut.elelab.SettlementActivity.SettlementActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.xmut.elelab.MyTool.data.MyData.experimentalScore;

public class DoExperimentFragment extends BaseQMUIFragment {

    private View view;
    private QMUIPopup mNormalPopup;

    @BindView(R.id.time)
    TimeRunTextView time;

    @BindView(R.id.move1)
    RelativeLayout test;
    @BindView(R.id.move2)
    RelativeLayout beaker;
    @BindView(R.id.move3)
    RelativeLayout volumetric;
    @BindView(R.id.move4)
    RelativeLayout reagentBottle;
    @BindView(R.id.experimental_score)
    TextView experimental_score;
    @BindView(R.id.score_variety)
    TextView score_variety;

    private Timer timer;
    private TimerTask task;
    private int currentTime = 0;
    private int numberOfTransfers = 0;
    private int confirmMessage = 0;
    private int numDropper = 0;

    private int mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.do_experiment_fragment, null);
        ButterKnife.bind(this, root);
        time.startTime(1200,"2");
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
        collisionDetection();
        view = root;
        return root;
    }

    @OnClick({R.id.menu})
    void onClickMenu(){
        showSimpleBottomSheetList(
                true, true, false, "请选择操作",
                5, true, false);
    }

    //试管的点击事件
    @OnClick({R.id.move1})
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

    // 试管的长按事件
//    @OnLongClick({R.id.move1})
//    public void onLongClickTest(View v){
//        TextView textView = new TextView(getContext());
//        textView.setLineSpacing(QMUIDisplayHelper.dp2px(getContext(), 4), 1.0f);
//        int padding = QMUIDisplayHelper.dp2px(getContext(), 20);
//        textView.setPadding(padding, padding, padding, padding);
//        textView.setText("通过 dimAmount() 设置背景遮罩");
//        textView.setTextColor(
//                QMUIResHelper.getAttrColor(getContext(), R.color.text_bg));
//        QMUISkinValueBuilder builder = QMUISkinValueBuilder.acquire();
//        builder.textColor(R.color.text_bgt);
//        QMUISkinHelper.setSkinValue(textView, builder);
//        builder.release();
//        mNormalPopup = QMUIPopups.popup(getContext(), QMUIDisplayHelper.dp2px(getContext(), 250))
//                .preferredDirection(QMUIPopup.DIRECTION_BOTTOM)
//                .view(textView)
//                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), 20))
//                .dimAmount(0.6f)
//                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
//                .onDismiss(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        Toast.makeText(getContext(), "onDismiss", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .show(v);
//    }

    //烧杯的点击事件
    @OnClick({R.id.move2})
    void onClickBeaker(View v) {
        QMUIPopups.quickAction(getContext(),
                QMUIDisplayHelper.dp2px(getContext(), 56),
                QMUIDisplayHelper.dp2px(getContext(), 56))
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_add_water).text("加水").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
                                builder.setTitle("添加")
                                        .setPlaceholder("在此输入添加的量（ml）")
                                        .setInputType(InputType.TYPE_CLASS_TEXT)
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .addAction("添加", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                CharSequence text = builder.getEditText().getText();
                                                if (text != null && text.length() > 0) {
                                                    advancedTips("加水成功！", QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
                                                    beaker.setBackgroundResource(R.mipmap.beaker20ml);
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(getActivity(), "请填入数量！", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .create(mCurrentDialogStyle).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_add).text("加入试剂").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                showSimpleBottomSheetList(
                                        true, true, false, "请选择试剂种类",
                                        3, true, false);
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_shaking).text("摇晃").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                advancedTips("摇晃成功");
                                beaker.setBackgroundResource(R.mipmap.beaker_r20ml);
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_cool_down).text("降温").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                advancedTips("降温成功");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_empty).text("清空").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                advancedTipsFailure("清空成功");
                                beaker.setBackgroundResource(R.mipmap.beaker);
                            }
                        }
                ))
                .show(v);
    }

    //容量瓶的点击事件
    @OnClick({R.id.move3})
    void onClickVolumetric(View v) {
        QMUIPopups.quickAction(getContext(),
                QMUIDisplayHelper.dp2px(getContext(), 56),
                QMUIDisplayHelper.dp2px(getContext(), 56))
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_add_water).text("加水").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
                                builder.setTitle("添加")
                                        .setPlaceholder("在此输入添加的量（ml）")
                                        .setInputType(InputType.TYPE_CLASS_TEXT)
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .addAction("添加", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                CharSequence text = builder.getEditText().getText();
                                                if (text != null && text.length() > 0) {
                                                    advancedTips("加水成功！");
                                                    volumetric.setBackgroundResource(R.mipmap.volumetric_g_98);
                                                    dialog.dismiss();
                                                } else {
                                                    Toast.makeText(getActivity(), "请填入数量！", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                        .create(mCurrentDialogStyle).show();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_add).text("加入试剂").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                showSimpleBottomSheetList(
                                        true, true, false, "请选择试剂种类",
                                        3, true, false);
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_insert).text("插玻璃棒").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                volumetric.setBackgroundResource(R.mipmap.volumetric_g);
                                advancedTips("插入成功！");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_take_out).text("取玻璃棒").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                volumetric.setBackgroundResource(R.mipmap.volumetric_98);
                                advancedTips("取出成功！");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_dropper).text("胶头滴管").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                if (numDropper == 0){
                                    volumetric.setBackgroundResource(R.mipmap.volumetric_99);
                                    numDropper++;
                                }else if (numDropper == 1){
                                    volumetric.setBackgroundResource(R.mipmap.volumetric_100);
                                    numDropper++;
                                }
                                advancedTips("成功滴加1ml！");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_shaking).text("摇晃").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                advancedTips("摇晃成功");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_empty).text("清空").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                volumetric.setBackgroundResource(R.mipmap.volumetric);
                                advancedTipsFailure("清空成功！");
                                numberOfTransfers = 0;
                                confirmMessage = 0;
                                numDropper = 0;
                            }
                        }
                ))
                .show(v);
    }

    //试剂瓶的点击事件
    @OnClick({R.id.move4})
    void onClickVolumetricFlask(View v) {
        QMUIPopups.quickAction(getContext(),
                QMUIDisplayHelper.dp2px(getContext(), 56),
                QMUIDisplayHelper.dp2px(getContext(), 56))
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_stamp).text("瓶塞").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                showCorksChoiceDialog();
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_attach_a_label).text("贴标签").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                showSimpleBottomSheetList(
                                        true, true, false, "请选择标签种类",
                                        4, true, false);
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_shaking).text("摇晃").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                advancedTips("摇晃成功");
                            }
                        }
                ))
                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_empty).text("清空").onClick(
                        new QMUIQuickAction.OnClickListener() {
                            @Override
                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                quickAction.dismiss();
                                reagentBottle.setBackgroundResource(R.mipmap.reagent_bottle);
                                advancedTipsFailure("清空成功！");
                                numberOfTransfers = 0;
                                confirmMessage = 0;
                                numDropper = 0;
                            }
                        }
                ))
                .show(v);
    }

    //试剂
    @OnClick({R.id.experiment_equipment})
    void onClickExpEqu(View v) {
        showSimpleBottomSheetList(
                true, true, false, "这是本次实验选择的试剂",
                3, true, false);
    }

    //提示
    @OnClick({R.id.tips})
    void onClickTips(View v) {
        showLongMessageDialog();
    }

    // ================================ 生成不同类型的BottomSheet
    private void showSimpleBottomSheetList(boolean gravityCenter,
                                           boolean addCancelBtn,
                                           boolean withIcon,
                                           CharSequence title,
                                           int itemCount,
                                           boolean allowDragDismiss,
                                           boolean withMark) {
        QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(getActivity());
        builder.setGravityCenter(gravityCenter)
                .setTitle(title)
                .setAddCancelBtn(addCancelBtn)
                .setAllowDrag(allowDragDismiss)
                .setNeedRightMark(withMark)
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        dialog.dismiss();
//                        Toast.makeText(getActivity(), "Item " + (position + 1), Toast.LENGTH_SHORT).show();
                        if (itemCount == 4){
                            showEditConcentrationTextDialog();
                        }else if (itemCount == 5){
                            if (position == 2){
                                new QMUIDialog.CheckBoxMessageDialogBuilder(getActivity())
                                        .setTitle("你确定要退出结算吗?")
                                        .setMessage("以后不再提示")
                                        .setChecked(true)
                                        .addAction("取消", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                advancedTipsFailure("取消退出");
                                            }
                                        })
                                        .addAction("退出", new QMUIDialogAction.ActionListener() {
                                            @Override
                                            public void onClick(QMUIDialog dialog, int index) {
                                                dialog.dismiss();
                                                Intent intent = new Intent(AppContext.getContext(), SettlementActivity.class);
                                                startActivity(intent);
                                            }
                                        })
                                        .create(mCurrentDialogStyle).show();
                            }
                        } else {
                            showEditTextDialog();
                        }
                    }
                });
        if(withMark){
            builder.setCheckedIndex(40);
        }
//        for (int i = 1; i <= itemCount; i++) {
//            if(withIcon){
//                builder.addItem(ContextCompat.getDrawable(getContext(), R.mipmap.test_tube), "Item " + i);
//            }else{
//                builder.addItem("Item " + i);
//            }
//        }
        if (itemCount == 4){
            builder.addItem("NaOH溶液");
            builder.addItem("NaCl溶液");
            builder.addItem("硫酸溶液");
        }else if (itemCount == 5){
            builder.addItem("继续");
            builder.addItem("暂停");
            builder.addItem("退出结算");
        } else {
            builder.addItem("NaOH");
            builder.addItem("NaCl");
            builder.addItem("98%浓硫酸");
        }
        builder.build().show();
    }

    //带输入框的确定取消
    //这里特指添加4g的NaOH
    private void showEditTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("计算")
                .setPlaceholder("在此输入添加的量")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("添加", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            beaker.setBackgroundResource(R.mipmap.beaker_naoh);
                            advancedTips("试剂添加成功！", QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入数量！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    //带输入框的确定取消
    //这里特指添加标签是1.0 mol/L的NaOH溶液
    private void showEditConcentrationTextDialog() {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("贴标签")
                .setPlaceholder("请输入浓度（mol/L）")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("确定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            reagentBottle.setBackgroundResource(R.mipmap.reagent_bottle100sq);
                            advancedTips("成功贴标签！", QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入浓度！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    //提示框
    private void showLongMessageDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("提示")
                .setMessage("题目要求：配置100ml 1.00mol/L的NaOH溶液\n相对原子质量：Na:23；O:16；H:1\n" +
                        "1、计算：所称取固体的质量或所量取液体的体积。公式：m = n / V\n" +
                        "\n" +
                        "2、称量：称量固体时要注意天平的精确程度，同样量取液体时，也要注意量筒和滴定管的精确程度。如托盘天平就不能称出5.85 g固体NaCl，量筒就不能量出5.25 mL液体的体积。因为他们的精确程度为0.1。建议使用电子天平。\n" +
                        "\n" +
                        "3、溶解：一般在烧杯中进行，在溶解过程中有的有热效应，故还要冷却，这是因为容量瓶的容量、规格是受温度限制的，如果未冷却，会因为热胀效应而产生误差。\n" +
                        "\n" +
                        "4、移液：转移液体时要用玻璃棒引流，且其下端一般应靠在容量瓶内壁的刻度线以下部位。\n" +
                        "\n" +
                        "5、洗涤：用蒸馏水洗涤烧杯和玻璃棒2～3次，其目的是使溶质尽可能地转移到容量瓶中，以防产生误差。\n" +
                        "\n" +
                        "6、定容：当向容量瓶中加水至刻度线1 cm～2 cm处时，再改用胶头滴管至刻度处。\n" +
                        "\n" +
                        "7、摇匀：这时如果液面低于刻度线，不要再加水。\n" +
                        "\n" +
                        "8、装瓶：容量瓶不能长时间盛放液体，应盛装在指定的试剂瓶中，并贴好标签。")
                .addAction("关闭", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    //碰撞检测
    public void collisionDetection(){
        startTimer();
    }

    private void initTimer() {
        // 初始化计时器
        task = new MyTask();
        timer = new Timer();
    }

    /**
     * java.util.Timer.schedule(TimerTask task, long delay, long period)：
     * 这个方法是说，delay/1000秒后执行task,然后进过period/1000秒再次执行task，
     * 这个用于循环任务，执行无数次，当然，你可以用timer.cancel();取消计时器的执行。
     */
    private void startTimer() {
        //启动计时器
        initTimer();
        try {
            timer.schedule(task, 100, 100);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            initTimer();
            timer.schedule(task, 100, 100);
        }
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
        }
        currentTime = 0;
    }

    class MyTask extends TimerTask {
        @Override
        public void run() {
            // 启动另一个UI线程
            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int[][] location = new int[4][2];
                    test.getLocationInWindow(location[0]);
                    beaker.getLocationInWindow(location[1]);
                    volumetric.getLocationInWindow(location[2]);
                    reagentBottle.getLocationInWindow(location[3]);
                    int x = location[0][0]; // view距离window 左边的距离（即x轴方向）
                    int y = location[0][1]; // view距离window 顶边的距离（即y轴方向）
                    //这三个result判断3个容器是否碰撞
                    boolean result = CheckRectCollsion(location[0][0], location[0][1], test.getWidth(), test.getHeight(),
                            location[1][0], location[1][1], beaker.getWidth(), beaker.getHeight());
                    boolean result1 = CheckRectCollsion(location[0][0], location[0][1], test.getWidth(), test.getHeight(),
                            location[2][0], location[2][1], volumetric.getWidth(), volumetric.getHeight());

                    // 烧杯和容量瓶
                    boolean result2 = CheckRectCollsion(location[1][0], location[1][1], beaker.getWidth(), beaker.getHeight(),
                            location[2][0], location[2][1], volumetric.getWidth(), volumetric.getHeight());

                    // 容量瓶和试剂瓶
                    boolean result3 = CheckRectCollsion(location[3][0], location[3][1], reagentBottle.getWidth(), reagentBottle.getHeight(),
                            location[2][0], location[2][1], volumetric.getWidth(), volumetric.getHeight());
                    if (result){
                        if(currentTime == 0){
                            stopTimer();
                            QMUIPopups.quickAction(getContext(),
                                    QMUIDisplayHelper.dp2px(getContext(), 56),
                                    QMUIDisplayHelper.dp2px(getContext(), 56))
                                    .shadow(true)
                                    .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                                    .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_transfer).text("转移").onClick(
                                            new QMUIQuickAction.OnClickListener() {
                                                @Override
                                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                                    quickAction.dismiss();
                                                    showSingleChoiceDialog();
                                                }
                                            }
                                    ))
                                    .show(test);
                            currentTime = 1;
                            startTimer();
                        }
                    }else if (result1){
                        if(currentTime == 0){
                            stopTimer();
                            QMUIPopups.quickAction(getContext(),
                                    QMUIDisplayHelper.dp2px(getContext(), 56),
                                    QMUIDisplayHelper.dp2px(getContext(), 56))
                                    .shadow(true)
                                    .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                                    .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_transfer).text("转移").onClick(
                                            new QMUIQuickAction.OnClickListener() {
                                                @Override
                                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                                    quickAction.dismiss();
                                                    showSingleChoiceDialog();
                                                }
                                            }
                                    ))
                                    .show(test);
                            currentTime = 1;
                            startTimer();
                        }
                    }else if (result2){
                        if(currentTime == 0){
                            stopTimer();
                            QMUIPopups.quickAction(Objects.requireNonNull(getContext()),
                                    QMUIDisplayHelper.dp2px(getContext(), 56),
                                    QMUIDisplayHelper.dp2px(getContext(), 56))
                                    .shadow(true)
                                    .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                                    .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_transfer).text("转移").onClick(
                                            new QMUIQuickAction.OnClickListener() {
                                                @Override
                                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                                    quickAction.dismiss();
                                                    showSingleChoiceDialog();
                                                }
                                            }
                                    ))
                                    .show(beaker);
                            currentTime = 1;
                            startTimer();
                        }
                    }else if (result3){
                        if(currentTime == 0){
                            stopTimer();
                            QMUIPopups.quickAction(Objects.requireNonNull(getContext()),
                                    QMUIDisplayHelper.dp2px(getContext(), 56),
                                    QMUIDisplayHelper.dp2px(getContext(), 56))
                                    .shadow(true)
                                    .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
                                    .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_transfer).text("转移").onClick(
                                            new QMUIQuickAction.OnClickListener() {
                                                @Override
                                                public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
                                                    quickAction.dismiss();
                                                    advancedTips("转移成功！");
                                                    volumetric.setBackgroundResource(R.mipmap.volumetric);
                                                    reagentBottle.setBackgroundResource(R.mipmap.reagent_bottle100);
                                                }
                                            }
                                    ))
                                    .show(reagentBottle);
                            currentTime = 1;
                            startTimer();
                        }
                    }else {
                        currentTime = 0;
                    }
                }
            });
        }
    }

    // 选择转移的数量 烧杯-容量瓶专用
    private void showSingleChoiceDialog() {
        final String[] items = new String[]{"全部转移", "转移1/2", "转移1/3", "自定义"};
        final int checkedIndex = 0;
        new QMUIDialog.CheckableDialogBuilder(getActivity())
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        if (which == 3){
                            transferSolution();
                        }else {
                            if (confirmMessage == 0){
                                showConfirmMessageDialog();
                                confirmMessage++;
                            }else {
                                transferBeakerToVolumetricFlask();
                            }
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    /**
     * 选择瓶塞 试剂瓶专用
     */
    private void showCorksChoiceDialog() {
        final String[] items = new String[]{"橡胶瓶塞", "玻璃瓶塞", "软木塞"};
        final int checkedIndex = 0;
        new QMUIDialog.CheckableDialogBuilder(getActivity())
                .setCheckedIndex(checkedIndex)
                .addItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getActivity(), "你选择了 " + items[which], Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        advancedTips("成功盖上软木塞！");
                        reagentBottle.setBackgroundResource(R.mipmap.reagent_bottle100s);
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    // 转移溶液
    private void transferSolution(){
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(getActivity());
        builder.setTitle("转移")
                .setPlaceholder("在此输入转移的量（ml）")
                .setInputType(InputType.TYPE_CLASS_TEXT)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("转移", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        CharSequence text = builder.getEditText().getText();
                        if (text != null && text.length() > 0) {
                            transferBeakerToVolumetricFlask();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(getActivity(), "请填入数量！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    // 确认转移 带选项的对话框
    private void showConfirmMessageDialog() {
        new QMUIDialog.CheckBoxMessageDialogBuilder(getActivity())
                .setTitle("你确定要转移吗?")
                .setMessage("以后不再提示")
                .setChecked(true)
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        advancedTipsFailure("转移取消");
                    }
                })
                .addAction("转移", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        transferBeakerToVolumetricFlask();
                    }
                })
                .create(mCurrentDialogStyle).show();
    }

    // 烧杯和容量瓶之间的转移 3次
    private void transferBeakerToVolumetricFlask(){
        if (numberOfTransfers == 0){
            beaker.setBackgroundResource(R.mipmap.beaker);
            volumetric.setBackgroundResource(R.mipmap.volumetric_g_20);
            numberOfTransfers++;
        }else if (numberOfTransfers == 1){
            beaker.setBackgroundResource(R.mipmap.beaker);
            volumetric.setBackgroundResource(R.mipmap.volumetric_g_40);
            numberOfTransfers++;
        }else if (numberOfTransfers == 2){
            beaker.setBackgroundResource(R.mipmap.beaker);
            volumetric.setBackgroundResource(R.mipmap.volumetric_g_60);
            numberOfTransfers++;
        }else {
            beaker.setBackgroundResource(R.mipmap.beaker);
        }
        advancedTips("转移成功！", QMUITipDialog.Builder.ICON_TYPE_SUCCESS);
    }

    //高级提示，带图标的
    private void advancedTips(String text, int number){
        final QMUITipDialog tipDialog;
//                        Toast.makeText(getActivity(), "转移成功！", Toast.LENGTH_SHORT).show();
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(number)
                .setTipWord(text)
                .create();
        tipDialog.show();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);
    }

    //高级提示，带默认图标的
    private void advancedTips(String text){
        final QMUITipDialog tipDialog;
//                        Toast.makeText(getActivity(), "转移成功！", Toast.LENGTH_SHORT).show();
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord(text)
                .create();
        tipDialog.show();
        scoreCalculation("+", 5);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);
    }

    //高级提示，带失败图标的
    private void advancedTipsFailure(String text){
        final QMUITipDialog tipDialog;
//                        Toast.makeText(getActivity(), "转移成功！", Toast.LENGTH_SHORT).show();
        tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_FAIL)
                .setTipWord(text)
                .create();
        tipDialog.show();
        scoreCalculation("-", 1);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 1500);
    }

    /**
     *
     * @param x1 矩形1的X坐标
     * @param y1 矩形1的Y坐标
     * @param w1 矩形1的宽
     * @param h1 矩形1的高
     * @param x2 矩形2的X坐标
     * @param y2  矩形2的Y坐标
     * @param w2 矩形2的宽
     * @param h2 矩形的高
     * @return
     */
    public boolean CheckRectCollsion(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
        if (x1 >= x2 && x1 >= x2 + w2) {
            return false;
        } else if (x1 <= x2 && x1 + w1 <= x2) {
            return false;
        } else if (y1 >= y2 && y1 >= y2 + h2) {
            return false;
        } else if (y1 <= y2 && y1 + h1 <= y2) {
            return false;
        }
        return true;
    }

    /**
     * 加分扣分的方法
     * 效果图片 http://blog.lks17.com/wp-content/uploads/2020/05/jiajian.png
     * @param type 加或者减
     * @param num 加减的数量
     */
    @SuppressLint({"SetTextI18n"})
    private void scoreCalculation(String type, int num){
        if (type.equals("+")){
            experimentalScore += num;
            score_variety.setTextColor(getResources().getColor(R.color.app_color_theme_41));
        }else if (type.equals("-")){
            experimentalScore -= num;
            score_variety.setTextColor(getResources().getColor(R.color.app_color_theme_11));
        }
        experimental_score.setText("实验分数：" + experimentalScore);
        score_variety.setText(type + num);
        score_variety.setVisibility(View.VISIBLE);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                score_variety.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }
}
