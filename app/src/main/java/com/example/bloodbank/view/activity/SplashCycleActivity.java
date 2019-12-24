package com.example.bloodbank.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.splashCycle.InroFragment;
import com.example.bloodbank.view.fragment.splashCycle.SplashFragment;

import butterknife.BindView;

import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadBoolean;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;

public class SplashCycleActivity extends BaseActivity {
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);
//      SplashFragment fragment = new SplashFragment();
//        loadFragment(fragment);
        splashScreen();

    }
    private void splashScreen() {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                if (LoadUserData(SplashCycleActivity.this) != null && LoadBoolean(SplashCycleActivity.this, REMEMBER_ME)) {
                    startActivity(new Intent(SplashCycleActivity.this, HomeCycleActivity.class));
                    finish();
                }else {
                    replaceFragment(getSupportFragmentManager(), R.id.fram, new InroFragment());
                }

            }
        };
        handler.postDelayed(r, 2000);

    }





}
