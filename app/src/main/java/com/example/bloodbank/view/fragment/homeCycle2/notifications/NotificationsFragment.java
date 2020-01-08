package com.example.bloodbank.view.fragment.homeCycle2.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.bloodbank.R;
import com.example.bloodbank.adapter.NotificationAdapter;
import com.example.bloodbank.data.api.ApiClient;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.notifications.getNotificationList.GetNotificationList;
import com.example.bloodbank.data.model.notifications.getNotificationList.GetNotificationListData;
import com.example.bloodbank.utils.OnEndLess;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getApiClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;
import static com.example.bloodbank.utils.RecycleTool.setRecycleTool;
import static com.example.bloodbank.utils.network.InternetState.isConnected;

public class NotificationsFragment extends BaSeFragment {


    @BindView(R.id.notifications_fragment_rv_notification_list)
    RecyclerView notificationsFragmentRvNotificationList;
    @BindView(R.id.load_more)
    RelativeLayout loadMore;
    @BindView(R.id.error_image)
    ImageView errorImage;
    @BindView(R.id.error_title)
    TextView errorTitle;
    @BindView(R.id.error_action)
    Button errorAction;
    @BindView(R.id.error_sub_view)
    View errorSubView;
    @BindView(R.id.donations_list_Fragment_sr_refresh_donations)
    SwipeRefreshLayout donationsListFragmentSrRefreshDonations;

    private LinearLayoutManager linearLayoutManager;
    private List<GetNotificationListData> notificationsDataList = new ArrayList<>();
    private NotificationAdapter notificationAdapter;
    private int maxPage = 0;
    private OnEndLess onEndLess;
    private ClientData clientData;
    private HomeCycleActivity homeCycleActivity;
    public NotificationsFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        if(true){

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
//    }
//        else {         root = inflater.inflate(R.layout.fragment_notifications, container, false);}
        ButterKnife.bind(this, root);
        clientData = LoadUserData(getActivity());
        homeCycleActivity= (HomeCycleActivity) getActivity();

        init();

        setUpActivity();

        homeCycleActivity.setNavigation("v");
        return root;
    }
    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        notificationsFragmentRvNotificationList.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);

                        getNotification(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }

            }
        };
        notificationsFragmentRvNotificationList.addOnScrollListener(onEndLess);

        notificationAdapter = new NotificationAdapter(getActivity(), notificationsDataList);
        notificationsFragmentRvNotificationList.setAdapter(notificationAdapter);

        getNotification(1);

        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reInit();
                getNotification(1);
            }
        });
    }

    private void getNotification(int page) {

        if (page == 1) {
            errorSubView.setVisibility(View.GONE);

        }

        if (isConnected(getActivity())) {

            getApiClient().getNotificationList(clientData.getApiToken(), page).enqueue(new Callback<GetNotificationList>() {
                @Override
                public void onResponse(Call<GetNotificationList> call, Response<GetNotificationList> response) {
                    try {

                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                notificationsDataList.addAll(response.body().getData().getData());
                                notificationAdapter.notifyDataSetChanged();
                            } else {
                                setError(getString(R.string.no_notification));
                            }

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GetNotificationList> call, Throwable t) {
                    try {

                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }
                }
            });

        } else {
            try {

                loadMore.setVisibility(View.GONE);
                donationsListFragmentSrRefreshDonations.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }

    private void setError(String errorTitleTxt) {
        if (notificationsDataList.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reInit();
                    getNotification(1);

                }
            };
            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.group49
                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        notificationsDataList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(getActivity(), notificationsDataList);
        notificationsFragmentRvNotificationList.setAdapter(notificationAdapter);
    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @Override
    public void onBack() {
        replaceFragment(getActivity().getSupportFragmentManager(), R.id.home_activity_fram
                , new NotificationsFragment());
    }
}
