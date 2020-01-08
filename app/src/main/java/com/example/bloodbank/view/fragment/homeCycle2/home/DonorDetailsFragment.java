package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.donations.DonationRequests.GeneralDonationRequestData;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DonorDetailsFragment extends BaSeFragment {


    @BindView(R.id.home_donor_details_img_back_recent)
    ImageView homeDonorDetailsImgBackRecent;
    @BindView(R.id.home_donor_details_donor_name_txt_title)
    TextView homeDonorDetailsDonorNameTxtTitle;
    @BindView(R.id.home_donor_details_donor_name_txt)
    TextView homeDonorDetailsDonorNameTxt;
    @BindView(R.id.home_donor_details_age_txt)
    TextView homeDonorDetailsAgeTxt;
    @BindView(R.id.home_donor_details_blood_type_txt)
    TextView homeDonorDetailsBloodTypeTxt;
    @BindView(R.id.home_donor_details_package_required_number_txt)
    TextView homeDonorDetailsPackageRequiredNumberTxt;
    @BindView(R.id.home_donor_details_hospital_txt)
    TextView homeDonorDetailsHospitalTxt;
    @BindView(R.id.home_donor_details_address_txt)
    TextView homeDonorDetailsAddressTxt;
    @BindView(R.id.home_donor_details_phone_txt)
    TextView homeDonorDetailsPhoneTxt;
    @BindView(R.id.home_donor_details_notes_txt)
    TextView homeDonorDetailsNotesTxt;
    @BindView(R.id.home_donor_details_location_img)
    ImageView homeDonorDetailsLocationImg;
    @BindView(R.id.home_donor_details_contact_btn)
    Button homeDonorDetailsContactBtn;
    public int donationId;
    private ClientData clientData;
    private GeneralDonationRequestData donationData;

    public DonorDetailsFragment() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_donor_details, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick({R.id.home_donor_details_img_back_recent, R.id.home_donor_details_location_img, R.id.home_donor_details_contact_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_donor_details_img_back_recent:
                break;
            case R.id.home_donor_details_location_img:
                break;
            case R.id.home_donor_details_contact_btn:
                break;
        }
    }
}