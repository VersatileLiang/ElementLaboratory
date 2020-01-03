package com.xmut.elelab.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.xmut.elelab.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageView netImg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        netImg = root.findViewById(R.id.home_img);
        Glide.with(this.getActivity())
                .load("https://i0.hdslb.com/bfs/album/90f974d6330c28728f3b28ea8051e3c5db2578db.jpg")
                .into(netImg);
        return root;
    }
}