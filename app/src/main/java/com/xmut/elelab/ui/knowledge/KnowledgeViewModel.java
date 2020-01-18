package com.xmut.elelab.ui.knowledge;

import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.R;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class KnowledgeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public KnowledgeViewModel() {
        mText = new MutableLiveData<>();
        String name = AppContext.getContext().getString(R.string.title_dashboard);
        mText.setValue(name);
    }

    public LiveData<String> getText() {
        return mText;
    }
}