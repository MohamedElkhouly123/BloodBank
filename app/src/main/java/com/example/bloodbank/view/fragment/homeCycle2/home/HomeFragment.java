package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

public class HomeFragment extends BaSeFragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        return root;
    }
}