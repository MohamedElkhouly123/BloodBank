package com.example.bloodbank.view.fragment.userCycle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.resetPassword.ResetPassword;
import com.example.bloodbank.utils.HelperMethod;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getApiClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.PHONE;
import static com.example.bloodbank.utils.HelperMethod.showToast;


public class NewPassword_Fragment extends BaSeFragment {


    @BindView(R.id.new_password_pin_code_etxt)
    EditText newPasswordPinCodeEtxt;
    @BindView(R.id.new_password_password_etxt)
    EditText newPasswordPasswordEtxt;
    @BindView(R.id.new_password_confirm_password_etxt)
    EditText newPasswordConfirmPasswordEtxt;
    private String phoneStr;
    private String passwordStr;
    private String passwordConfirmStr;
    private String pinCodeStr;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_new_password, container, false);
        ButterKnife.bind(this, root);


        return root;
    }

    @OnClick(R.id.new_password_btn_change)
    public void onViewClicked() {
        phoneStr=newPasswordPasswordEtxt.getText().toString().trim();
        passwordStr=newPasswordConfirmPasswordEtxt.getText().toString().trim();
        pinCodeStr=newPasswordPinCodeEtxt.getText().toString().trim();
        phoneStr=LoadData(getActivity(), PHONE);

        if (passwordConfirmStr.length() < 3) {
            showToast(getActivity(),"Password must more  than 3 chars");
            return;
        }

        if (passwordStr.length() < 3 ) {
            showToast(getActivity(),"Password must more  than 3 chars");
            return;
        }

        onCall(passwordStr, passwordConfirmStr,pinCodeStr,phoneStr);

    }

    private void onCall(String password, String passwordConfirm, String pinCode,String phone) {
        if (progressDialog == null) {
            HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
            }
        }
        getApiClient().userNewPassword(password,passwordConfirm,pinCode,phone).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {

                    }

                } catch (Exception e) {


                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                HelperMethod.dismissProgressDialog();

            }
        });
    }
}