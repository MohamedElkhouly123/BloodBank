package com.example.bloodbank.view.fragment.splashCycle;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashFragment extends BaSeFragment {


    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_splash, container, false);
        ButterKnife.bind(this, root);
        initListener();
        splashScreen();
        InroFragment fragment = new InroFragment();
        loadFragment(fragment);
        return root;
    }
    private void splashScreen() {
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
//                viewModelUser.checkUser(getApplicationContext());
            }
        };
        handler.postDelayed(r, 6000);

    }

    private void initListener() {
//        progressBar.setVisibility(View.VISIBLE);
//        viewModelUser = ViewModelProviders.of(this).get(ViewModelUser.class);
//        viewModelUser.checkUSerIfLogin().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean response) {
//                if (response) {
//                    goToMain();
//                } else {
//                    goToLogin();
//                    //Error happen
//                }
//            }
//        });
    }

//    private void goToMain() {
//
//    }
//
//    private void goToLogin() {
//
//    }
private void loadFragment(Fragment fragment) {
    // load fragment
    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.user_activity_fram, fragment);
    transaction.addToBackStack(null);
    transaction.commit();
}
}