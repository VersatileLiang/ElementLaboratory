package com.xmut.elelab.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xmut.elelab.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class KnowledgeFragment extends Fragment {

    private KnowledgeViewModel knowledgeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        knowledgeViewModel =
                ViewModelProviders.of(this).get(KnowledgeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_knowledge, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        knowledgeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        ImageView netImg;
        netImg = root.findViewById(R.id.knowledge_img);
        Glide.with(this.getActivity())
                .load("https://i0.hdslb.com/bfs/album/7e9bc9f6109f2a36167390a32a0ca96416ddcd0e.png")
                .into(netImg);
        return root;
    }
}