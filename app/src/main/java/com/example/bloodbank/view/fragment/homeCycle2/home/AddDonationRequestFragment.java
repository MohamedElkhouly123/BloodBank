package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.view.activity.MapsActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.bloodbank.utils.HelperMethod.showToast;

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
    @BindView(R.id.home_add_donation_request_hospital_address_etxt)
    EditText homeAddDonationRequestHospitalAddressEtxt;
    private static final String TAG = "HomeCycleActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_add_donation_request, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    private void init(){

                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);

    }

    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            showToast(getActivity(),"You can't make map requests");

        }
        return false;
    }

    @OnClick({R.id.home_add_donation_request_hospital_location_img, R.id.home_add_donation_request_send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_add_donation_request_hospital_location_img:
                if(isServicesOK()){
                    init();
                }

                break;
            case R.id.home_add_donation_request_send_btn:
                break;
        }
    }

}