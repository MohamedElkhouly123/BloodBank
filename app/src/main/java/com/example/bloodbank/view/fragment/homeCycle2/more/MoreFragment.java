package com.example.bloodbank.view.fragment.homeCycle2.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.utils.LogOutDialog;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.utils.HelperMethod.replaceFragment;

public class MoreFragment extends BaSeFragment {

   private   BottomNavigationView navBar;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more, container, false);
        ButterKnife.bind(this, root);
        navBar = getActivity().findViewById(R.id.nav_view);
        navBar.setVisibility(View.VISIBLE);

        return root;
    }

    @OnClick({R.id.more_my_favourte_lay, R.id.more_contact_us_lay, R.id.more_about_app_lay, R.id.more_my_put_rate_on_store_lay, R.id.more_nottiffy_setting_lay, R.id.more_sign_out_lay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_my_favourte_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new FavuoritesFragment());

                break;
            case R.id.more_contact_us_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new ContactUsFragment());
                navBar.setVisibility(View.GONE);
                break;
            case R.id.more_about_app_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new AboutAppFragment());

                break;
            case R.id.more_my_put_rate_on_store_lay:

                break;
            case R.id.more_nottiffy_setting_lay:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new NottifySettingFragment());

                break;
            case R.id.more_sign_out_lay:
                new LogOutDialog().showDialog(getActivity());
                break;
        }
    }
}