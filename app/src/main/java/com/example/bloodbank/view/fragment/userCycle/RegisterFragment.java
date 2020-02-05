package com.example.bloodbank.view.fragment.userCycle;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.EmptySpinnerAdapter;
import com.example.bloodbank.data.model.DateTxt;
import com.example.bloodbank.data.model.client.Client;
import com.example.bloodbank.utils.HelperMethod;
import com.example.bloodbank.utils.ToastCreator;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import static com.example.bloodbank.utils.GeneralRequest.getData;
import static com.example.bloodbank.utils.HelperMethod.showCalender;
import static com.example.bloodbank.utils.HelperMethod.showToast;
import static com.example.bloodbank.utils.ToastCreator.onCreateErrorToast;
import static com.example.bloodbank.utils.ToastCreator.onCreateSuccessToast;
import static com.example.bloodbank.utils.validation.Validation.validationAllEmpty;
import static com.example.bloodbank.utils.validation.Validation.validationConfirmPassword;
import static com.example.bloodbank.utils.validation.Validation.validationEmail;
import static com.example.bloodbank.utils.validation.Validation.validationLength;
import static com.example.bloodbank.utils.validation.Validation.validationPassword;
import static com.example.bloodbank.utils.validation.Validation.validationPhone;


public class RegisterFragment extends BaSeFragment {


    @BindView(R.id.register_name_etxt)
    EditText registerNameEtxt;
    @BindView(R.id.register_email_etxt)
    EditText registerEmailEtxt;
    @BindView(R.id.register_birth_day_etxt)
    EditText registerBirthDayEtxt;
    @BindView(R.id.register_blood_type_id_spinner)
    Spinner registerBloodTypeIdSpinner;
    @BindView(R.id.register_blood_type_etxt)
    EditText registerBloodTypeEtxt;
    @BindView(R.id.register_last_date_to_donation_etxt)
    EditText registerLastDateToDonationEtxt;
    @BindView(R.id.register_governrate_etxt)
    EditText registerGovernrateEtxt;
    @BindView(R.id.register_governrate_id_spinner)
    Spinner registerGovernrateIdSpinner;
    @BindView(R.id.register_city_etxt)
    EditText registerCityEtxt;
    @BindView(R.id.register_city_id_spinner)
    Spinner registerCityIdSpinner;
    @BindView(R.id.register_phone_etxt)
    EditText registerPhoneEtxt;
    @BindView(R.id.register_password_etxt)
    EditText registerPasswordEtxt;
    @BindView(R.id.register_confirm_password_etxt)
    EditText registerConfirmPasswordEtxt;
    @BindView(R.id.register_enter_btn)
    Button registerEnterBtn;
    @BindView(R.id.city_container)
    CardView cityContainer;

    private String nameStr;
    private String emailStr;
    private String birth_day_Str;
    private String cityIdStr;
    private String phoneStr;
    private String last_date_to_donation_Str;
    private String passwordStr;
    private String confirm_passwordStr;
    private String blood_type_IdStr;
    private String blood_type_spinnerStr, governrate_spinnerStr, citySpinnerStr;
    private boolean blood_type_flag = true;
    private boolean governrate_flag = true;
    private boolean cityFlag = true;
    private EmptySpinnerAdapter bloodTypeAdapter, governrateAdapter, cityAdapter;
    private int cityId;
    private int bloodTypeId;
    private DateTxt birthdayDate, lastDonationDate;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, root);
        SpinnerExecute();
        setDates();

        return root;
    }


    @OnClick(R.id.register_enter_btn)
    public void onViewClicked() {


        nameStr = registerNameEtxt.getText().toString().trim();
        emailStr = registerEmailEtxt.getText().toString().trim();
        birth_day_Str = registerBirthDayEtxt.getText().toString().trim();
        last_date_to_donation_Str = registerLastDateToDonationEtxt.getText().toString().trim();
        phoneStr = registerPhoneEtxt.getText().toString().trim();
        passwordStr = registerPasswordEtxt.getText().toString().trim();
        confirm_passwordStr = registerConfirmPasswordEtxt.getText().toString().trim();
        blood_type_IdStr = String.valueOf(bloodTypeId);
        cityIdStr = String.valueOf(cityId);


//        if (phoneStr.length() != 11) {
//            showToast(getActivity(), "Password must more  than 3 chars");
//
//            return;
//        }
//
//        if (passwordStr.length() < 3) {
//            showToast(getActivity(), "Password must more  than 3 chars");
//            return;
//        }
//        if (confirm_passwordStr.length() < 3) {
//            showToast(getActivity(), "Password must more  than 3 chars");
//            return;
//        }
//        if (!blood_type_flag) {
//            showToast(getActivity(), "must select blood type value");
//            return;
//        }
//        if (!governrate_flag) {
//            showToast(getActivity(), "must select governrate value");
//            return;
//        }
//        if (!cityFlag) {
//            showToast(getActivity(), "must select city value");
//            return;
//        }
        onValidation();

    }

    private void setDates() {
        DecimalFormat mFormat = new DecimalFormat("00");
        Calendar calander = Calendar.getInstance();
        String cDay = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.DAY_OF_MONTH))));
        String cMonth = mFormat.format(Double.valueOf(String.valueOf(calander.get(Calendar.MONTH + 1))));
        String cYear = String.valueOf(calander.get(Calendar.YEAR));

        lastDonationDate = new DateTxt(cDay, cMonth, cYear, cDay + "-" + cMonth + "-" + cYear);
        birthdayDate = new DateTxt("01", "01", "1990", "01-01-1990");

    }
    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        editTexts.add(registerNameEtxt);
        editTexts.add(registerEmailEtxt);
        editTexts.add(registerPhoneEtxt);
        editTexts.add(registerPasswordEtxt);
        editTexts.add(registerConfirmPasswordEtxt);

        spinners.add(registerBloodTypeIdSpinner);
        spinners.add(registerGovernrateIdSpinner);
        spinners.add(registerCityIdSpinner);

        if (!validationAllEmpty(editTexts, spinners, getString(R.string.empty)) &&
                registerBirthDayEtxt.getText().toString().equals("") &&
                registerLastDateToDonationEtxt.getText().toString().equals("")) {


            onCreateErrorToast(getActivity(), getString(R.string.empty));
            return;
        }


        if (!validationLength(registerNameEtxt, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), registerEmailEtxt)) {

            return;
        }

        if (!validationLength(getActivity(), registerBirthDayEtxt.getText().toString().trim()
                , getString(R.string.invalid_brd))) {
            return;
        }

        if (!validationLength(getActivity(), registerLastDateToDonationEtxt.getText().toString().trim()
                , getString(R.string.invalid_last_date))) {
            return;
        }

        if (registerBloodTypeIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_blood_type));
            return;
        }

        if (registerGovernrateIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_government));
            return;
        }

        if (registerCityIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_city));
            return;
        }

        if (!validationPhone(getActivity(), registerPhoneEtxt)) {
            return;
        }

        if (!validationPassword(registerPasswordEtxt, 6, getString(R.string.invalid_password))) {
            return;
        }

        if (!validationConfirmPassword(getActivity(), registerPasswordEtxt, registerConfirmPasswordEtxt)) {
            return;
        }
        onCall(nameStr, emailStr, birth_day_Str, cityIdStr, phoneStr, last_date_to_donation_Str, passwordStr, confirm_passwordStr, blood_type_IdStr);


    }    private void SpinnerExecute() {

        AdapterView.OnItemSelectedListener cityListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (i == 0) {
                    cityFlag = false;

                } else {
                    cityFlag = true;
                    cityId = i;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        AdapterView.OnItemSelectedListener bloodTypeListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (i == 0) {
                    blood_type_flag = false;


                } else {
                    blood_type_flag = true;
                    bloodTypeId = i;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        bloodTypeAdapter = new EmptySpinnerAdapter(getActivity());
        getData(getApiClient().getBloodType(), bloodTypeAdapter, "mbbb", registerBloodTypeIdSpinner, bloodTypeListener);
        AdapterView.OnItemSelectedListener govenrateListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                if (i == 0) {
                    governrate_flag = false;
                    cityContainer.setVisibility(View.GONE);


                } else {
                    governrate_flag = true;
                    String govenrateId = String.valueOf(i);
                    cityAdapter = new EmptySpinnerAdapter(getActivity());
                    getData(getApiClient().getCity(govenrateId), cityAdapter, "mbbjkkb", registerCityIdSpinner, cityListener);
                    cityContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        governrateAdapter = new EmptySpinnerAdapter(getActivity());
        getData(getApiClient().getGovenorate(), governrateAdapter, "mbbklhb", registerGovernrateIdSpinner, govenrateListener);

    }

    private void onCall(String nameStr, String emailStr, String birth_day_str, String cityIdStr, String phoneStr, String last_date_to_donation_str, String passwordStr, String confirm_passwordStr, String blood_type_idStr) {

        if (progressDialog == null) {
            HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
        } else {
            if (!progressDialog.isShowing()) {
                HelperMethod.showProgressDialog(getActivity(), getActivity().getString(R.string.wait));
            }
        }
        getApiClient().userRegister(nameStr, emailStr, birth_day_Str, cityIdStr, phoneStr, last_date_to_donation_Str, passwordStr, confirm_passwordStr, blood_type_IdStr).enqueue(new Callback<Client>() {


            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                try {
                    HelperMethod.dismissProgressDialog();
                    if (response.body().getStatus() == 1) {
//                    String msg= response.errorBody().toString();
//                        showToast(getActivity(),msg);

                        SaveData(getActivity(), USER_DATA, response.body().getData());
                        SaveData(getActivity(), USER_PASSWORD, passwordStr);
                        SaveData(getActivity(), REMEMBER_ME, true);
                        onCreateSuccessToast(getActivity(), response.body().getMsg());
                        Intent intent = new Intent(getActivity(), HomeCycleActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                    onCreateErrorToast(getActivity(), response.body().getMsg());

                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                try {
//                    String msg = t.getMessage();
//                    showToast(getActivity(), msg);
                    HelperMethod.dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));

                } catch (Exception e) {

                }
            }
        });

    }

    @OnClick({R.id.register_birth_day_etxt, R.id.register_last_date_to_donation_etxt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_birth_day_etxt:
                showCalender(getActivity(), getString(R.string.select_date), registerBirthDayEtxt, birthdayDate);

                break;
            case R.id.register_last_date_to_donation_etxt:
                showCalender(getActivity(), getString(R.string.select_date), registerLastDateToDonationEtxt, lastDonationDate);

                break;
        }
    }
}