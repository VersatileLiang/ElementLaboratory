package com.xmut.elelab.ExperimentActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.MyTool.base.BaseActivity;
import com.xmut.elelab.R;

/**
 * 显示实验列表的界面
 */
public class ExperimentActivity extends BaseActivity {

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
            }
        });
    }
}
