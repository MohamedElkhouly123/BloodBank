package com.example.bloodbank.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridLayout;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.homeCycle2.home.HomeFragment;
import com.example.bloodbank.view.fragment.homeCycle2.more.MoreFragment;
import com.example.bloodbank.view.fragment.homeCycle2.notifications.NotificationsFragment;
import com.example.bloodbank.view.fragment.homeCycle2.update_my_info.UpdateMyInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;



import static com.example.bloodbank.utils.HelperMethod.replaceFragment;


public class HomeCycleActivity extends BaseActivity implements  BottomNavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        replaceFragment(getSupportFragmentManager(), R.id.fram, new HomeFragment());

        BottomNavigationView navigation =  findViewById(R.id.nav_view);
        navigation.setOnNavigationItemSelectedListener(this);



    }










    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_home) {
            replaceFragment(getSupportFragmentManager(), R.id.fram, new HomeFragment());
        } else if (id == R.id.navigation_update_my_info) {
            replaceFragment(getSupportFragmentManager(), R.id.fram, new UpdateMyInfoFragment());
        } else if (id == R.id.navigation_notifications) {
            replaceFragment(getSupportFragmentManager(), R.id.fram, new NotificationsFragment());

        } else if (id == R.id.navigation_more_setting) {
            replaceFragment(getSupportFragmentManager(), R.id.fram, new MoreFragment());
        }



        return true;
    }


}
