package com.xmut.elelab.ExperimentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.R;

/**
 * 显示实验列表的界面
 */
public class ExperimentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiment);
        LinearLayout linearLayout = findViewById(R.id.experiment_1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppContext.getContext(), DoExperimentActivity.class);
                startActivity(intent);
//                Toast.makeText(ExperimentActivity.this,"点击了实验1，即将开始实验！",
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
