package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.DonationAdapter;
import com.example.bloodbank.adapter.EmptySpinnerAdapter;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.donations.DonationRequests.GeneralDonationRequestData;
import com.example.bloodbank.data.model.donations.DonationRequests.GetAllDonationRequests;
import com.example.bloodbank.utils.OnEndLess;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;
import static com.example.bloodbank.utils.network.InternetState.isConnected;

public class DonationRequestsFragment extends BaSeFragment {


    @BindView(R.id.home_donation_requests_blood_type_id_spinner)
    Spinner homeDonationRequestsBloodTypeIdSpinner;
    @BindView(R.id.home_donation_requests_governrate_id_spinner)
    Spinner homeDonationRequestsGovernrateIdSpinner;
    @BindView(R.id.home_donation_requests_recycler_view)
    RecyclerView homeDonationRequestsRecyclerView;
    @BindView(R.id.home_donation_requests_floating_action_btn)
    FloatingActionButton homeDonationRequestsFloatingActionBtn;
    private BottomNavigationView navBar;
    @BindView(R.id.donations_list_Fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout donationsListFragmentSFlShimmerDonations;
    @BindView(R.id.donations_list_Fragment_sr_refresh_donations)
    SwipeRefreshLayout donationsListFragmentSrRefreshDonations;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    TextView errorAction;
    @BindView(R.id.error_sub_view)
    View errorSubView;
//    @BindView(R.id.donations_list_Fragment_ll_sub_view)
//    LinearLayout donationsListFragmentLlSubView;
private EmptySpinnerAdapter bloodTypesAdapter, gaviermentAdapter;
    private LinearLayoutManager linearLayout;
    public List<GeneralDonationRequestData> donationDataList = new ArrayList<>();
    public DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;
    private ClientData clientData;

    private String lang = "en";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_donation_requests, container, false);
        ButterKnife.bind(this, root);
        navBar = getActivity().findViewById(R.id.nav_view);
        clientData = LoadUserData(getActivity());

        if (bloodTypesAdapter != null) {
            bloodTypesAdapter = new EmptySpinnerAdapter(getActivity());
//            getSpinnerData(getActivity(), donationsListFragmentSpBloodTypes, bloodTypesAdapter, getString(R.string.select_blood_type),
//                    getClient().getBloodTypes(), null, 0, false);
        }

        if (gaviermentAdapter != null) {
            gaviermentAdapter = new EmptySpinnerAdapter(getActivity());
//            getSpinnerData(getActivity(), donationsListFragmentSpGovernment, gaviermentAdapter, getString(R.string.select_government),
//                    getClient().getGovernorates(), null, 0, false);
        }

        init();
        return root;
    }

    private void init() {
        linearLayout = new LinearLayoutManager(getActivity());
        homeDonationRequestsRecyclerView.setLayoutManager(linearLayout);

        onEndLess = new OnEndLess(linearLayout, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);
                        if (Filter) {
                            onFilter(current_page);
                        } else {
                            getDonations(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        homeDonationRequestsRecyclerView.addOnScrollListener(onEndLess);

        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        homeDonationRequestsRecyclerView.setAdapter(donationAdapter);

        if (donationDataList.size() == 0) {
            getDonations(1);
        } else {
            donationsListFragmentSFlShimmerDonations.stopShimmer();
            donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
        }

        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (Filter) {
                    onFilter(1);
                } else {
                    getDonations(1);
                }

            }
        });
    }

    private void getDonations(int page) {
        Call<GetAllDonationRequests> call = getApiClient().getAllDonationRequests(clientData.getApiToken(), page);

        startCall(call, page);
    }

    private void onFilter(int page) {

        Filter = true;

        Call<GetAllDonationRequests> call = getApiClient().getAllDonationRequestsFilter(
                clientData.getApiToken(), page, bloodTypesAdapter.selectedId, gaviermentAdapter.selectedId);

        startCall(call, page);
    }

    private void startCall(Call<GetAllDonationRequests> call, int page) {
        errorSubView.setVisibility(View.GONE);
        if (page == 1) {
            reInit();
            errorSubView.setVisibility(View.GONE);
            donationsListFragmentSFlShimmerDonations.startShimmer();
            donationsListFragmentSFlShimmerDonations.setVisibility(View.VISIBLE);
        }

        if (isConnected(getActivity())) {

            call.enqueue(new Callback<GetAllDonationRequests>() {
                @Override
                public void onResponse(Call<GetAllDonationRequests> call, Response<GetAllDonationRequests> response) {
                    try {
                        donationsListFragmentSFlShimmerDonations.stopShimmer();
                        donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                donationDataList.addAll(response.body().getData().getData());
                                donationAdapter.notifyDataSetChanged();
                            } else {
                                View.OnClickListener action = new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        AddDonationRequestFragment createDonationFragment = new AddDonationRequestFragment();
//                                        createDonationFragment.donationsListFragment = DonationsListFragment.this;
                                        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, createDonationFragment);
                                    }
                                };
//                                setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_transfusion
//                                        , getString(R.string.no_donations), getString(R.string.add_new_donation), action);
                            }


                        } else {
                            setError(getString(R.string.error_list));
                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GetAllDonationRequests> call, Throwable t) {
                    try {
                        donationsListFragmentSFlShimmerDonations.stopShimmer();
                        donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }

                }
            });

        } else {
            try {
                donationsListFragmentSFlShimmerDonations.stopShimmer();
                donationsListFragmentSFlShimmerDonations.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                donationsListFragmentSrRefreshDonations.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }

    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        donationDataList = new ArrayList<>();
        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        homeDonationRequestsRecyclerView.setAdapter(donationAdapter);
    }

    private void setError(String errorTitleTxt) {
        if (donationDataList.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Filter) {
                        onFilter(1);
                    } else {
                        getDonations(1);
                    }

                }
            };
//            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.ic_transfusion
//                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

//    @Override
//    public void onBack() {
//        getActivity().finish();
//    }

    @OnClick(R.id.home_donation_requests_floating_action_btn)
    public void onViewClicked() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram, new AddDonationRequestFragment());
        navBar.setVisibility(View.GONE);

    }


    @Override
    public void onBack() {
//        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram
//                , new NotificationsFragment());
        getActivity().finish();
        navBar.setVisibility(View.VISIBLE);


    }
}