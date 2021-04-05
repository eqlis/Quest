package com.example.qu.ui.quests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuestsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public QuestsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is quests fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}