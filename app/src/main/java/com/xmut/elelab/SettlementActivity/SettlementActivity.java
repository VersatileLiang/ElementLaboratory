package com.xmut.elelab.SettlementActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmut.elelab.MainActivity;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.base.BaseActivity;
import com.xmut.elelab.R;

import java.text.DecimalFormat;

import static com.xmut.elelab.MyTool.data.MyData.experimentalScore;

public class SettlementActivity extends BaseActivity {

    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        Glide.with(this)
                .load("http://blog.liangkaisong.top/wp-content/uploads/2020/05/20200506183856-e1588761682491.png")
                .into((ImageView)findViewById(R.id.exp_img));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        textView = this.findViewById(R.id.fraction);
        button = this.findViewById(R.id.back_exp_list);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppContext.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        DecimalFormat df = new DecimalFormat("#.00");
        textView.setText("实验总分：\n" + df.format(experimentalScore * 0.81 + 20) + "分");
    }
}
