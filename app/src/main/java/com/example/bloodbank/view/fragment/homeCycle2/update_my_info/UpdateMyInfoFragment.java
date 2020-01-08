package com.example.bloodbank.view.fragment.homeCycle2.update_my_info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateMyInfoFragment extends BaSeFragment {


    @BindView(R.id.update_my_info_name_etxt)
    EditText updateMyInfoNameEtxt;
    @BindView(R.id.update_my_info_email_etxt)
    EditText updateMyInfoEmailEtxt;
    @BindView(R.id.update_my_info_birth_day_etxt)
    EditText updateMyInfoBirthDayEtxt;
    @BindView(R.id.update_my_info_blood_type_id_spinner)
    Spinner updateMyInfoBloodTypeIdSpinner;
    @BindView(R.id.update_my_info_blood_type_etxt)
    EditText updateMyInfoBloodTypeEtxt;
    @BindView(R.id.update_my_info_last_date_to_donation_etxt)
    EditText updateMyInfoLastDateToDonationEtxt;
    @BindView(R.id.update_my_info_governrate_etxt)
    EditText updateMyInfoGovernrateEtxt;
    @BindView(R.id.update_my_info_governrate_id_spinner)
    Spinner updateMyInfoGovernrateIdSpinner;
    @BindView(R.id.update_my_info_city_etxt)
    EditText updateMyInfoCityEtxt;
    @BindView(R.id.update_my_info_city_id_spinner)
    Spinner updateMyInfoCityIdSpinner;
    @BindView(R.id.update_my_info_phone_etxt)
    EditText updateMyInfoPhoneEtxt;
    @BindView(R.id.update_my_info_password_etxt)
    EditText updateMyInfoPasswordEtxt;
    @BindView(R.id.update_my_info_confirm_password_etxt)
    EditText updateMyInfoConfirmPasswordEtxt;
    @BindView(R.id.update_my_info_confirm_btn)
    Button updateMyInfoConfirmBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_update_my_info, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.update_my_info_confirm_btn)
    public void onViewClicked() {
    }
}