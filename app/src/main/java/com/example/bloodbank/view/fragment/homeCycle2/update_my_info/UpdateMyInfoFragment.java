package com.example.bloodbank.view.fragment.homeCycle2.update_my_info;

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
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.utils.HelperMethod;
import com.example.bloodbank.utils.ToastCreator;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getApiClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.REMEMBER_ME;
import static com.example.bloodbank.data.local.SharedPreferencesManger.SaveData;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_DATA;
import static com.example.bloodbank.data.local.SharedPreferencesManger.USER_PASSWORD;
import static com.example.bloodbank.utils.GeneralRequest.getData;
import static com.example.bloodbank.utils.HelperMethod.showCalender;
import static com.example.bloodbank.utils.ToastCreator.onCreateErrorToast;
import static com.example.bloodbank.utils.ToastCreator.onCreateSuccessToast;
import static com.example.bloodbank.utils.validation.Validation.validationAllEmpty;
import static com.example.bloodbank.utils.validation.Validation.validationConfirmPassword;
import static com.example.bloodbank.utils.validation.Validation.validationEmail;
import static com.example.bloodbank.utils.validation.Validation.validationLength;
import static com.example.bloodbank.utils.validation.Validation.validationPassword;
import static com.example.bloodbank.utils.validation.Validation.validationPhone;

public class UpdateMyInfoFragment extends BaSeFragment {


    @BindView(R.id.update_my_info_name_etxt)
    EditText updateMyInfoNameEtxt;
    @BindView(R.id.update_my_info_email_etxt)
    EditText updateMyInfoEmailEtxt;
    @BindView(R.id.update_my_info_birth_day_etxt)
    EditText updateMyInfoBirthDayEtxt;
    @BindView(R.id.update_my_info_blood_type_id_spinner)
    Spinner updateMyInfoBloodTypeIdSpinner;
    @BindView(R.id.update_my_info_blood_type_etxt)
    EditText updateMyInfoBloodTypeEtxt;
    @BindView(R.id.update_my_info_last_date_to_donation_etxt)
    EditText updateMyInfoLastDateToDonationEtxt;
    @BindView(R.id.update_my_info_governrate_etxt)
    EditText updateMyInfoGovernrateEtxt;
    @BindView(R.id.update_my_info_governrate_id_spinner)
    Spinner updateMyInfoGovernrateIdSpinner;
    @BindView(R.id.update_my_info_city_etxt)
    EditText updateMyInfoCityEtxt;
    @BindView(R.id.update_my_info_city_id_spinner)
    Spinner updateMyInfoCityIdSpinner;
    @BindView(R.id.update_my_info_phone_etxt)
    EditText updateMyInfoPhoneEtxt;
    @BindView(R.id.update_my_info_password_etxt)
    EditText updateMyInfoPasswordEtxt;
    @BindView(R.id.update_my_info_confirm_password_etxt)
    EditText updateMyInfoConfirmPasswordEtxt;
    @BindView(R.id.update_my_info_confirm_btn)
    Button updateMyInfoConfirmBtn;
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
    private int blood_type_spinnerStr, governrate_spinnerStr, citySpinnerStr;
    private boolean blood_type_flag = true;
    private boolean governrate_flag = true;
    private boolean cityFlag = true;
    private EmptySpinnerAdapter bloodTypeAdapter, governrateAdapter, cityAdapter;
    private int cityId;
    private int bloodTypeId;
    private DateTxt birthdayDate, lastDonationDate;
    private ProgressDialog progressDialog;
    private ClientData clientData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_update_my_info, container, false);
        ButterKnife.bind(this, root);
        SpinnerExecute();
        setUpActivity();
        clientData = LoadUserData(getActivity());
        setUserData();
        return root;
    }

    private void setUserData() {
        blood_type_spinnerStr = clientData.getClient().getBloodType().getId();
        governrate_spinnerStr = clientData.getClient().getGovernorate().getId();
        citySpinnerStr = clientData.getClient().getCity().getId();

        updateMyInfoNameEtxt.setText(clientData.getClient().getName());
        updateMyInfoEmailEtxt.setText(clientData.getClient().getEmail());
        updateMyInfoPhoneEtxt.setText(clientData.getClient().getPhone());
        updateMyInfoPasswordEtxt.setText(LoadData(getActivity(), USER_PASSWORD));
        updateMyInfoConfirmPasswordEtxt.setText(LoadData(getActivity(), USER_PASSWORD));

        updateMyInfoBirthDayEtxt.setText(clientData.getClient().getBirthDate());
        updateMyInfoLastDateToDonationEtxt.setText(clientData.getClient().getDonationLastDate());



    }

    private void onValidation() {

        List<EditText> editTexts = new ArrayList<>();
        List<Spinner> spinners = new ArrayList<>();

        editTexts.add(updateMyInfoNameEtxt);
        editTexts.add(updateMyInfoEmailEtxt);
        editTexts.add(updateMyInfoPhoneEtxt);
        editTexts.add(updateMyInfoPasswordEtxt);
        editTexts.add(updateMyInfoConfirmPasswordEtxt);

        spinners.add(updateMyInfoBloodTypeIdSpinner);
        spinners.add(updateMyInfoGovernrateIdSpinner);
        spinners.add(updateMyInfoCityIdSpinner);

        if (!validationAllEmpty(editTexts, spinners, getString(R.string.empty)) &&
                birth_day_Str.equals("") &&
                last_date_to_donation_Str.equals("")) {


            onCreateErrorToast(getActivity(), getString(R.string.empty));
            return;
        }


        if (!validationLength(updateMyInfoNameEtxt, getString(R.string.invalid_user_name), 3)) {
            return;
        }

        if (!validationEmail(getActivity(), updateMyInfoEmailEtxt)) {

            return;
        }

        if (!validationLength(getActivity(), birth_day_Str
                , getString(R.string.invalid_brd))) {
            return;
        }

        if (!validationLength(getActivity(), last_date_to_donation_Str
                , getString(R.string.invalid_last_date))) {
            return;
        }

        if (updateMyInfoBloodTypeIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_blood_type));
            return;
        }

        if (updateMyInfoGovernrateIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_government));
            return;
        }

        if (updateMyInfoCityIdSpinner.getSelectedItemPosition() == 0) {
            onCreateErrorToast(getActivity(), getString(R.string.select_city));
            return;
        }

        if (!validationPhone(getActivity(), updateMyInfoPhoneEtxt)) {
            return;
        }

        if (!validationPassword(updateMyInfoPasswordEtxt, 6, getString(R.string.invalid_password))) {
            return;
        }

        if (!validationConfirmPassword(getActivity(), updateMyInfoPasswordEtxt, updateMyInfoConfirmPasswordEtxt)) {
            return;
        }
        onCall(nameStr, emailStr, birth_day_Str, cityIdStr, phoneStr, last_date_to_donation_Str, passwordStr, confirm_passwordStr, blood_type_IdStr);


    }

    private void SpinnerExecute() {

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
        getData(getApiClient().getBloodType(), bloodTypeAdapter, "mbbb", updateMyInfoBloodTypeIdSpinner, bloodTypeListener);
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
                    getData(getApiClient().getCity(govenrateId), cityAdapter, "mbbjkkb", updateMyInfoCityIdSpinner, cityListener);
                    cityContainer.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        governrateAdapter = new EmptySpinnerAdapter(getActivity());
        getData(getApiClient().getGovenorate(), governrateAdapter, "mbbklhb", updateMyInfoGovernrateIdSpinner, govenrateListener);

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
                        onCreateSuccessToast(getActivity(), response.body().getMsg());

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


    @OnClick({R.id.update_my_info_birth_day_etxt, R.id.update_my_info_last_date_to_donation_etxt, R.id.update_my_info_confirm_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.update_my_info_birth_day_etxt:
                showCalender(getActivity(), getString(R.string.select_date), updateMyInfoBirthDayEtxt, birthdayDate);

                break;
            case R.id.update_my_info_last_date_to_donation_etxt:
                showCalender(getActivity(), getString(R.string.select_date), updateMyInfoLastDateToDonationEtxt, lastDonationDate);

                break;
            case R.id.update_my_info_confirm_btn:
                nameStr = updateMyInfoNameEtxt.getText().toString().trim();
                emailStr = updateMyInfoEmailEtxt.getText().toString().trim();
                birth_day_Str = updateMyInfoBirthDayEtxt.getText().toString().trim();
                last_date_to_donation_Str = updateMyInfoLastDateToDonationEtxt.getText().toString().trim();
                phoneStr = updateMyInfoPhoneEtxt.getText().toString().trim();
                passwordStr = updateMyInfoPasswordEtxt.getText().toString().trim();
                confirm_passwordStr = updateMyInfoConfirmPasswordEtxt.getText().toString().trim();
                blood_type_IdStr = String.valueOf(bloodTypeId);
                cityIdStr = String.valueOf(cityId);
                onValidation();

                break;
        }
    }
}