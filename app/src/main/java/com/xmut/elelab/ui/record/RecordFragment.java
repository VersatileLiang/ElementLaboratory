package com.xmut.elelab.ui.record;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmut.elelab.R;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class RecordFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RecordViewModel recordViewModel = ViewModelProviders.of(this).get(RecordViewModel.class);
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        recordViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ImageView netImg = root.findViewById(R.id.record_img);
        Glide.with(Objects.requireNonNull(this.getActivity()))
                .load("http://liangkaisong.top/i/1/qj8886343343.jpg")
                .into(netImg);
        return root;
    }
}