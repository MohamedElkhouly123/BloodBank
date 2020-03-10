package com.example.bloodbank.view.fragment.userCycle;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.bloodbank.R;
import com.example.bloodbank.data.model.client.Client;
import com.example.bloodbank.utils.HelperMethod;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getApiClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;
import static com.example.bloodbank.utils.HelperMethod.showToast;


public class LoginFragment extends BaSeFragment {


    @BindView(R.id.login_forget_id)
    TextView loginForgetId;

    @BindView(R.id.login_phone_etxt)
    EditText loginPhoneEtxt;
    @BindView(R.id.login_password_etxt)
    EditText loginPasswordEtxt;
    @BindView(R.id.login_remember_me_chbox)
    CheckBox loginRememberMeChbox;

    @BindView(R.id.login_creat_new_account_txt)
    TextView loginCreatNewAccountTxt;

    private String phoneStr;
    private String passwordStr;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, root);


        return root;
    }




    @OnClick({R.id.login_remember_me_chbox, R.id.login_forget_id, R.id.login_btn_next, R.id.login_creat_new_account_txt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_remember_me_chbox:
                break;
            case R.id.login_forget_id:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new ResetPassword_Fragment());
                break;
            case R.id.login_btn_next:
                phoneStr=loginPhoneEtxt.getText().toString().trim();
                passwordStr=loginPasswordEtxt.getText().toString().trim();
                if (phoneStr.length() != 11) {
                    showToast(getActivity(),"Password must more  than 3 chars");

                    return;
                }

                if (passwordStr.length() < 3) {
                    showToast(getActivity(),"Password must more  than 3 chars");
                    return;
                }

                onCall(phoneStr, passwordStr);

                break;
            case R.id.login_creat_new_account_txt:
                RegisterFragment register = new RegisterFragment();
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new RegisterFragment());
                break;
        }
    }

    private void onCall(String phone, String password) {
        if (progressDialog == null) {
            HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
            }
        }
        getApiClient().userLogin(phone, password).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {

                        SaveData(getActivity(), USER_DATA, response.body().getData());
                        SaveData(getActivity(), REMEMBER_ME, loginRememberMeChbox.isChecked());
                        SaveData(getActivity(), USER_PASSWORD, password);

                        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }

                } catch (Exception e) {


                }

            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                HelperMethod.dismissProgressDialog();

            }
        });
    }
}