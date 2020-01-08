package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddDonationRequestFragment extends BaSeFragment {


    @BindView(R.id.home_add_donation_request_name_etxt)
    EditText homeAddDonationRequestNameEtxt;
    @BindView(R.id.home_add_donation_request_email_etxt)
    EditText homeAddDonationRequestEmailEtxt;
    @BindView(R.id.home_add_donation_request_blood_type_id_spinner)
    Spinner homeAddDonationRequestBloodTypeIdSpinner;
    @BindView(R.id.home_add_donation_request_blood_type_etxt)
    EditText homeAddDonationRequestBloodTypeEtxt;
    @BindView(R.id.home_add_donation_request_bags_number_etxt)
    EditText homeAddDonationRequestBagsNumberEtxt;
    @BindView(R.id.home_add_donation_request_hospital_location_etxt)
    TextView homeAddDonationRequestHospitalLocationEtxt;
    @BindView(R.id.home_add_donation_request_governrate_etxt)
    EditText homeAddDonationRequestGovernrateEtxt;
    @BindView(R.id.home_add_donation_request_governrate_id_spinner)
    Spinner homeAddDonationRequestGovernrateIdSpinner;
    @BindView(R.id.home_add_donation_request_city_etxt)
    EditText homeAddDonationRequestCityEtxt;
    @BindView(R.id.home_add_donation_request_city_id_spinner)
    Spinner homeAddDonationRequestCityIdSpinner;
    @BindView(R.id.home_add_donation_request_phone_etxt)
    EditText homeAddDonationRequestPhoneEtxt;
    @BindView(R.id.home_add_donation_request_notes)
    EditText homeAddDonationRequestNotes;
    @BindView(R.id.home_add_donation_request_send_btn)
    Button homeAddDonationRequestSendBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_add_donation_request, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick({R.id.home_add_donation_request_hospital_location_etxt, R.id.home_add_donation_request_send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_add_donation_request_hospital_location_etxt:
                break;
            case R.id.home_add_donation_request_send_btn:
                break;
        }
    }
}