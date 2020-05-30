package com.xmut.elelab.ExperimentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.MyView.ZoomView;
import com.xmut.elelab.MyTool.base.BaseActivity;
import com.xmut.elelab.MyTool.base.BaseQMUIActivity;
import com.xmut.elelab.R;

import butterknife.OnClick;

import static com.xmut.elelab.MyTool.AppContext.AppContext.getContext;

/**
 * 显示实验列表的界面
 */
public class ExperimentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        Glide.with(this)
                .load("http://blog.liangkaisong.top/wp-content/uploads/2020/05/20200506183856-e1588761682491.png")
                .into((ImageView)findViewById(R.id.exp_img));
        Button button = findViewById(R.id.start_exp);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoExperimentActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout linearLayout = findViewById(R.id.add_qc);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "预设实验无法修改", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
