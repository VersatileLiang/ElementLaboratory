package com.xmut.elelab.ui.home;

import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.R;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        String home = AppContext.getContext().getString(R.string.rotation_chart);
        mText.setValue(home);
    }

    public LiveData<String> getText() {
        return mText;
    }
}