package com.example.bloodbank.view.fragment.userCycle;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import static com.example.bloodbank.data.local.SharedPreferencesManger.PHONE;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;
import static com.example.bloodbank.utils.HelperMethod.showToast;


public class ResetPassword_Fragment extends BaSeFragment {


    @BindView(R.id.reset_phone_etxt)
    EditText resetPhoneEtxt;

    @BindView(R.id.reset_password_btn_sent)
    Button resetPasswordBtnSent;
    private ProgressDialog progressDialog;

    private String phoneStr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, root);


        return root;
    }


    private void onCall(String phone) {
        if (progressDialog == null) {
            HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
            }
        }
        getApiClient().userResetPassword(phone).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {

                try {
                    HelperMethod.dismissProgressDialog();

                    if (response.body().getStatus() == 1) {
                        SaveData(getActivity(), PHONE, phone);
                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new NewPassword_Fragment());

                    }

                } catch (Exception e) {


                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                showToast(getActivity(), "error");
                HelperMethod.dismissProgressDialog();

            }
        });
    }


    @OnClick({R.id.reset_password_btn_sent})
    public void onViewClicked(View view) {



        phoneStr = resetPhoneEtxt.getText().toString().trim();
        if (phoneStr.length() != 11) {
            showToast(getActivity(), "phone must equal 11 digit");

            return;
        }
        onCall(phoneStr);

    }
}