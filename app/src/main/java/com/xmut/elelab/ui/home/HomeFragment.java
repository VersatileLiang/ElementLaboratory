package com.xmut.elelab.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.xmut.elelab.ExperimentActivity.ExperimentActivity;
import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView netImg;
    private Button experimentButton;//开始实验按钮

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        netImg = root.findViewById(R.id.home_img);
        Glide.with(root)
                .load("http://liangkaisong.top/i/1/qj8860735275.jpg")
                .into(netImg);

        experimentButton = root.findViewById(R.id.experiment_button);
        experimentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AppContext.getContext(), ExperimentActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }
}