package com.xmut.elelab.ui.notifications;

import com.xmut.elelab.MyTool.AppContext.AppContext;
import com.xmut.elelab.R;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificationsViewModel() {
        mText = new MutableLiveData<>();
        String name = AppContext.getContext().getString(R.string.title_notifications);
        mText.setValue(name);
    }

    public LiveData<String> getText() {
        return mText;
    }
}