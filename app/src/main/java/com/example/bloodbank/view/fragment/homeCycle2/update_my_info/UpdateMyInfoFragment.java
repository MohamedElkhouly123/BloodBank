package com.example.bloodbank.view.fragment.homeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

public class UpdateMyInfoFragment extends BaSeFragment {

    private UpdateMyInfoViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(UpdateMyInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_update_my_info, container, false);
        final TextView textView = root.findViewById(R.id.text_update);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}