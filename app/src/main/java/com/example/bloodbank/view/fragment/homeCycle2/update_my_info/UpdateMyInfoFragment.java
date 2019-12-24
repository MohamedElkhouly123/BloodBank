package com.example.bloodbank.view.fragment.homeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;


import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

public class UpdateMyInfoFragment extends BaSeFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_update_my_info, container, false);

        return root;
    }
}