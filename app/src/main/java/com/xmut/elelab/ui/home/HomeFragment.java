package com.xmut.elelab.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xmut.elelab.ExperimentActivity.ExperimentActivity;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.MyView.ZoomView;
import com.xmut.elelab.MyTool.base.BaseQMUIFragment;
import com.xmut.elelab.R;

public class HomeFragment extends BaseQMUIFragment {

    private ImageView netImg;
    private LinearLayout experimentButton;//开始实验按钮

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);
        netImg = root.findViewById(R.id.home_img);
        Glide.with(root)
                .load("https://blog.liangkaisong.top/wp-content/uploads/2020/04/1.jpg")
                .into(netImg);

        Glide.with(root)
                .load("http://blog.liangkaisong.top/wp-content/uploads/2020/04/JU3IS1D3G288AUTG39HL.png")
                .into((ImageView)root.findViewById(R.id.image_experiment_1));
        Glide.with(root)
                .load("http://blog.liangkaisong.top/wp-content/uploads/2020/05/氢氧化铁胶体.png")
                .into((ImageView)root.findViewById(R.id.image_experiment_2));
        Glide.with(root)
                .load("http://blog.liangkaisong.top/wp-content/uploads/2020/05/yansefanying.png")
                .into((ImageView)root.findViewById(R.id.image_experiment_3));
        experimentButton = root.findViewById(R.id.experiment_1);
//        experimentButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent intent = new Intent(AppContext.getContext(), ExperimentActivity.class);
////                startActivity(intent);
//                Toast.makeText(getContext(), "请选择难度", Toast.LENGTH_SHORT).show();
//            }
//        });
        return root;
    }

    @OnClick({R.id.experiment_1,R.id.experiment_2})
    void onClickExperiment_1(View v) {
        Intent intent = new Intent(AppContext.getContext(), ExperimentActivity.class);
        startActivity(intent);
//        Toast.makeText(getContext(), "请选择难度", Toast.LENGTH_SHORT).show();
//        QMUIPopups.quickAction(getContext(),
//                QMUIDisplayHelper.dp2px(getContext(), 56),
//                QMUIDisplayHelper.dp2px(getContext(), 56))
//                .shadow(true)
//                .edgeProtection(QMUIDisplayHelper.dp2px(getContext(), (int)(20* ZoomView.scale)))
//                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_nandu1).text("简单").onClick(
//                        new QMUIQuickAction.OnClickListener() {
//                            @Override
//                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
//                                quickAction.dismiss();
//                                Intent intent = new Intent(AppContext.getContext(), ExperimentActivity.class);
//                                startActivity(intent);
//                            }
//                        }
//                ))
//                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_nandu2).text("普通").onClick(
//                        new QMUIQuickAction.OnClickListener() {
//                            @Override
//                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
//                                quickAction.dismiss();
//                                Toast.makeText(getContext(), "请先通过简单难度", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ))
//                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_nandu3).text("困难").onClick(
//                        new QMUIQuickAction.OnClickListener() {
//                            @Override
//                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
//                                quickAction.dismiss();
//                                Toast.makeText(getContext(), "请先通过普通难度", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ))
//                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_nandu4).text("噩梦").onClick(
//                        new QMUIQuickAction.OnClickListener() {
//                            @Override
//                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
//                                quickAction.dismiss();
//                                Toast.makeText(getContext(), "请先通过困难难度", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ))
//                .addAction(new QMUIQuickAction.Action().icon(R.drawable.ic_nandu5).text("难到放弃").onClick(
//                        new QMUIQuickAction.OnClickListener() {
//                            @Override
//                            public void onClick(QMUIQuickAction quickAction, QMUIQuickAction.Action action, int position) {
//                                quickAction.dismiss();
//                                Toast.makeText(getContext(), "请先通过噩梦难度", Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ))
//                .show(v);
    }
}