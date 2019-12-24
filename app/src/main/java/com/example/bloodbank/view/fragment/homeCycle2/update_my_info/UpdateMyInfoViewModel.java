package com.example.bloodbank.view.fragment.homeCycle2.update_my_info;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UpdateMyInfoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UpdateMyInfoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}