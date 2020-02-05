package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.utils.HelperMethod.replaceFragment;

public class DonationRequestsFragment extends BaSeFragment {


    @BindView(R.id.home_donation_requests_blood_type_id_spinner)
    Spinner homeDonationRequestsBloodTypeIdSpinner;
    @BindView(R.id.home_donation_requests_governrate_id_spinner)
    Spinner homeDonationRequestsGovernrateIdSpinner;
    @BindView(R.id.home_donation_requests_recycler_view)
    RecyclerView homeDonationRequestsRecyclerView;
    @BindView(R.id.home_donation_requests_floating_action_btn)
    FloatingActionButton homeDonationRequestsFloatingActionBtn;
    private BottomNavigationView navBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_donation_requests, container, false);
        ButterKnife.bind(this, root);
        navBar = getActivity().findViewById(R.id.nav_view);
        return root;
    }

    @OnClick(R.id.home_donation_requests_floating_action_btn)
    public void onViewClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new AddDonationRequestFragment());
        navBar.setVisibility(View.GONE);

    }
    @Override
    public void onBack() {
//        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram
//                , new NotificationsFragment());
        getActivity().finish();
        navBar.setVisibility(View.VISIBLE);


    }
}