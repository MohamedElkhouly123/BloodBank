package com.example.bloodbank.view.fragment.homeCycle2.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.NotificationSettingAdapter;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.bloodbank.data.model.generalResponse.GeneralResponseData2;
import com.example.bloodbank.data.model.notificationSetting.NotificationSetting;
import com.example.bloodbank.utils.network.InternetState;
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
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.utils.HelperMethod.dismissProgressDialog;
import static com.example.bloodbank.utils.HelperMethod.setInitRecyclerViewAsGridLayoutManager;
import static com.example.bloodbank.utils.HelperMethod.showProgressDialog;
import static com.example.bloodbank.utils.ToastCreator.onCreateErrorToast;
import static com.example.bloodbank.utils.ToastCreator.onCreateSuccessToast;

public class NottifySettingFragment extends BaSeFragment {


    @BindView(R.id.more_nottifies_setting_back_img)
    ImageView moreNottifiesSettingBackImg;
    @BindView(R.id.more_nottifies_setting_drop_down_blood_types)
    TextView moreNottifiesSettingDropDownBloodTypes;
    @BindView(R.id.more_nottifies_setting_drop_down_governrates)
    TextView moreNottifiesSettingDropDownGovernrates;
    @BindView(R.id.more_nottifies_setting_drop_down_blood_types_Layout)
    LinearLayout moreNottifiesSettingDropDownBloodTypesLayout;
    @BindView(R.id.more_nottifies_setting_drop_down_governrates_layout)
    LinearLayout moreNottifiesSettingDropDownGovernratesLayout;
    @BindView(R.id.more_nottifies_setting_drop_down_blood_types_recycler_view2)
    RecyclerView moreNottifiesSettingDropDownBloodTypesRecyclerView2;
    @BindView(R.id.more_nottifies_setting_drop_down_governrates_recycler_view)
    RecyclerView moreNottifiesSettingDropDownGovernratesRecyclerView;
    private String dropDownGovernratesFlag = "plus";
    private String dropDownBloodTypesFlag = "plus";
    private GridLayoutManager gridLayoutManager;
    private NotificationSettingAdapter bloodsAdapter, GovernAdapter;

    private List<GeneralResponseData2> governoratesList = new ArrayList<>();
    private List<GeneralResponseData2> bloodsList = new ArrayList<>();
    private List<String> oldBloodTypes = new ArrayList<>();
    private List<String> oldGovernorates = new ArrayList<>();
    private ClientData clientData;

    public NottifySettingFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_nottify_setting, container, false);
        ButterKnife.bind(this, root);
        clientData = LoadUserData(getActivity());
        setUpActivity();

//        homeCycleActivity.setNavigation("g");
        onCall(true);
        return root;
    }

    private void onCall(final boolean state) {

        if (InternetState.isConnected(getActivity())) {
            showProgressDialog(getActivity(), getString(R.string.wait));

            Call<NotificationSetting> call;

            if (state) {
                call = getApiClient().getYourNotificationSetting(clientData.getApiToken());
            } else {
                call = getApiClient().changeNotificationSetting(clientData.getApiToken(), GovernAdapter.ids, bloodsAdapter.ids);
            }

            call.enqueue(new Callback<NotificationSetting>() {
                @Override
                public void onResponse(Call<NotificationSetting> call, Response<NotificationSetting> response) {
                    try {

                        dismissProgressDialog();
                        if (state) {

                            if (response.body().getStatus() == 1) {

                                oldBloodTypes = response.body().getData().getBloodTypes();
                                oldGovernorates = response.body().getData().getGovernorates();
                                getBloodTypes();
                                getGovernorates();

                            } else {
                                onCreateErrorToast(getActivity(), response.body().getMsg());
                            }

                        } else {
                            onCreateSuccessToast(getActivity(), response.body().getMsg());
                        }

                    } catch (Exception e) {

                    }

                }

                @Override
                public void onFailure(Call<NotificationSetting> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });

        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }

    private void getBloodTypes() {

        setInitRecyclerViewAsGridLayoutManager(getActivity(),moreNottifiesSettingDropDownBloodTypesRecyclerView2 , gridLayoutManager, 3);

        if (InternetState.isConnected(getActivity())) {
//            showProgressDialog(getActivity(), getString(R.string.wait));
            Call<GeneralResponse> call;

            call = getApiClient().getBloodType();
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    try {
                        dismissProgressDialog();
                        if (response.body().getStatus() == 1) {
                            if (bloodsList.size() == 0) {
//                                visible(notificationSettingsFragmentRelBloodsGone, notificationSettingsFragmentIv);
                                bloodsList = new ArrayList<>();
                                bloodsList.addAll(response.body().getData());
                                bloodsAdapter = new NotificationSettingAdapter(getActivity(), getActivity(), bloodsList, oldBloodTypes);
                                moreNottifiesSettingDropDownBloodTypesRecyclerView2.setAdapter(bloodsAdapter);
                            }
                        } else {
                            dismissProgressDialog();
                            onCreateErrorToast(getActivity(), response.body().getMsg());

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });
        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }

    private void getGovernorates() {

        setInitRecyclerViewAsGridLayoutManager(getActivity(), moreNottifiesSettingDropDownGovernratesRecyclerView, gridLayoutManager, 3);

        if (InternetState.isConnected(getActivity())) {
//            showProgressDialog(getActivity(), getString(R.string.wait));
            Call<GeneralResponse> call;

            call = getApiClient().getGovenorate();
            call.enqueue(new Callback<GeneralResponse>() {
                @Override
                public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                    try {
                        dismissProgressDialog();
                        if (response.body().getStatus() == 1) {
                            if (governoratesList.size() == 0) {
//                                visible(notificationSettingsFragmentRelGovernoratesGone, notificationSettingsFragmentIv2);
                                bloodsList = new ArrayList<>();
                                governoratesList.addAll(response.body().getData());
                                GovernAdapter = new NotificationSettingAdapter(getActivity(), getActivity(), governoratesList, oldGovernorates);
                                moreNottifiesSettingDropDownGovernratesRecyclerView.setAdapter(GovernAdapter);
                            }
                        } else {
                            dismissProgressDialog();
                            onCreateErrorToast(getActivity(), response.body().getMsg());

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GeneralResponse> call, Throwable t) {
                    dismissProgressDialog();
                    onCreateErrorToast(getActivity(), getString(R.string.error));
                }
            });
        } else {
            dismissProgressDialog();
            onCreateErrorToast(getActivity(), getResources().getString(R.string.error_inter_net));
        }

    }

    @OnClick({R.id.more_nottifies_setting_back_img, R.id.more_nottifies_setting_drop_down_blood_types, R.id.more_nottifies_setting_drop_down_governrates, R.id.more_nottifies_setting_dsave_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_nottifies_setting_back_img:
                onBack();
                break;
            case R.id.more_nottifies_setting_drop_down_blood_types:
                if (dropDownBloodTypesFlag == "plus") {
                    dropDownBloodTypesFlag = "minus";
                    moreNottifiesSettingDropDownBloodTypesLayout.setVisibility(View.GONE);
                    moreNottifiesSettingDropDownBloodTypes.setBackgroundResource(R.drawable.ic_add_black_24dp);

                } else {
                    dropDownBloodTypesFlag = "plus";
                    moreNottifiesSettingDropDownBloodTypesLayout.setVisibility(View.VISIBLE);
                    moreNottifiesSettingDropDownBloodTypes.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                }

                break;
            case R.id.more_nottifies_setting_drop_down_governrates:
                if (dropDownGovernratesFlag == "plus") {
                    dropDownGovernratesFlag = "minus";
                    moreNottifiesSettingDropDownGovernratesLayout.setVisibility(View.GONE);
                    moreNottifiesSettingDropDownGovernrates.setBackgroundResource(R.drawable.ic_add_black_24dp);

                } else {
                    dropDownGovernratesFlag = "plus";
                    moreNottifiesSettingDropDownGovernratesLayout.setVisibility(View.VISIBLE);
                    moreNottifiesSettingDropDownGovernrates.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                }

                break;
            case R.id.more_nottifies_setting_dsave_btn:
                onCall(false);

                break;
        }
    }

    @OnClick(R.id.more_nottifies_setting_drop_down_blood_types)
    public void onViewClicked() {
    }
}