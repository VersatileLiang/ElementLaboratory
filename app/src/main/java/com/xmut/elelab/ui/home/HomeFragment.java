package com.xmut.elelab.ui.home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
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
        Glide.with(root)
                .load("http://blog.lks17.com/wp-content/uploads/2020/05/VXZV99BF6DPC5OVSSQ4P.png")
                .into((ImageView)root.findViewById(R.id.image_experiment_4));
        Glide.with(root)
                .load("http://img5.imgtn.bdimg.com/it/u=3158714781,737531336&fm=26&gp=0.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_5));
        Glide.with(root)
                .load("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3333605711,3895278817&fm=26&gp=0.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_6));
        Glide.with(root)
                .load("https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00160-1312.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_7));
        Glide.with(root)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1590760705484&di=7004ae8db9b3558d14805df84216d35a&imgtype=0&src=http%3A%2F%2Fgss0.baidu.com%2F-vo3dSag_xI4khGko9WTAnF6hhy%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3Df1323c4356b5c9ea62a60be7e0099a36%2Fdbb44aed2e738bd476b5b879ac8b87d6267ff9e6.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_8));
        Glide.with(root)
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4213760708,4138298075&fm=26&gp=0.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_9));
        Glide.with(root)
                .load("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=774776193,528860138&fm=26&gp=0.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_10));
        Glide.with(root)
                .load("https://ns-strategy.cdn.bcebos.com/ns-strategy/upload/fc_big_pic/part-00239-2251.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_11));
        Glide.with(root)
                .load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=191312848,1827822599&fm=26&gp=0.jpg")
                .into((ImageView)root.findViewById(R.id.image_experiment_12));


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

    @OnClick({R.id.experiment_1})
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