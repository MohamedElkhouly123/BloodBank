package com.example.bloodbank.view.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.homeCycle2.home.HomeFragment;
import com.example.bloodbank.view.fragment.homeCycle2.more.MoreFragment;
import com.example.bloodbank.view.fragment.homeCycle2.notifications.NotificationsFragment;
import com.example.bloodbank.view.fragment.homeCycle2.update_my_info.UpdateMyInfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;

import static com.example.bloodbank.utils.HelperMethod.replaceFragment;


public class HomeCycleActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


//    @BindView(R.id.nav_view)
//    BottomNavigationView navView;
      private BottomNavigationView navView;
    public HomeCycleActivity() {
        // Required empty public constructor
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);
        replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new HomeFragment());

                 navView = (BottomNavigationView) findViewById(R.id.nav_view);
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelected());
        navView.setOnNavigationItemSelectedListener(this);



    }

    public void setNavigation(String visibility) {
        navView = (BottomNavigationView) findViewById(R.id.nav_view);
        if(visibility.equals("v")){
            navView.setVisibility(View.VISIBLE);
        }else  if(visibility.equals("g")){
            navView.setVisibility(View.GONE);
        }
//        if (id!= 0) {
//            navView.setId(id);
//
//
//        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navigation_home) {
            replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new HomeFragment());
        } else if (id == R.id.navigation_update_my_info) {
            replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new UpdateMyInfoFragment());
        } else if (id == R.id.navigation_notifications) {
            replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new NotificationsFragment());

        } else if (id == R.id.navigation_more_setting) {
            replaceFragment(getSupportFragmentManager(), R.id.user_activity_fram, new MoreFragment());
        }


        return true;
    }


}
