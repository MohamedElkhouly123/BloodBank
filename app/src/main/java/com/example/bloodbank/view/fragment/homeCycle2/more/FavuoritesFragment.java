package com.example.bloodbank.view.fragment.homeCycle2.more;

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
import com.example.bloodbank.adapter.EmptySpinnerAdapter;
import com.example.bloodbank.adapter.PostsAndFavouritesAdapter;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.posts.getAllPosts.GetAllPosts;
import com.example.bloodbank.data.model.posts.getAllPosts.PostsData;
import com.example.bloodbank.utils.OnEndLess;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.facebook.shimmer.ShimmerFrameLayout;

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
import static com.example.bloodbank.utils.RecycleTool.setRecycleTool;
import static com.example.bloodbank.utils.network.InternetState.isConnected;

public class FavuoritesFragment extends BaSeFragment {


    @BindView(R.id.more_my_favourite_img_back_recent)
    ImageView moreMyFavouriteImgBackRecent;
    @BindView(R.id.more_my_favourite_recycler_view)
    RecyclerView moreMyFavouriteRecyclerView;
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
    @BindView(R.id.notifications_fragment_s_fl_shimmer_donations)
    ShimmerFrameLayout notificationsFragmentSFlShimmerDonations;

    private LinearLayoutManager linearLayoutManager;
    private ClientData clientData;
    private EmptySpinnerAdapter categoriesAdapter;
    public List<PostsData> favouritepostsDataList = new ArrayList<>();
    public PostsAndFavouritesAdapter favouritePostsAdapter;
    private int maxPage = 0;
    private OnEndLess onEndLess;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_favourite, container, false);
        ButterKnife.bind(this, root);
        clientData = LoadUserData(getActivity());
        homeCycleActivity= (HomeCycleActivity) getActivity();
        init();

        setUpActivity();

        return root;
    }
    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        moreMyFavouriteRecyclerView.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);


                            getFavouritePosts(current_page);

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }

            }
        };
        moreMyFavouriteRecyclerView.addOnScrollListener(onEndLess);

        favouritePostsAdapter = new PostsAndFavouritesAdapter(getActivity(), favouritepostsDataList, false);
        moreMyFavouriteRecyclerView.setAdapter(favouritePostsAdapter);


            getFavouritePosts(1);

        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reInit();

                    getFavouritePosts(1);

            }
        });
    }

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        favouritepostsDataList = new ArrayList<>();
        favouritePostsAdapter = new PostsAndFavouritesAdapter(getActivity(), favouritepostsDataList, false);
        moreMyFavouriteRecyclerView.setAdapter(favouritePostsAdapter);
    }

    private void getFavouritePosts(int page) {
        Call<GetAllPosts> call;


        call = getApiClient().getAllFavouritePosts(clientData.getApiToken(), page);

        call(call, page);
    }

    private void call(Call<GetAllPosts> call, int page) {
        if (page == 1) {
            errorSubView.setVisibility(View.GONE);
            notificationsFragmentSFlShimmerDonations.startShimmer();
            notificationsFragmentSFlShimmerDonations.setVisibility(View.VISIBLE);
        }
        if (isConnected(getActivity())) {

            call.enqueue(new Callback<GetAllPosts>() {
                @Override
                public void onResponse(Call<GetAllPosts> call, Response<GetAllPosts> response) {
                    try {
                        notificationsFragmentSFlShimmerDonations.stopShimmer();
                        notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);

                        if (response.body().getStatus() == 1) {
                            maxPage = response.body().getData().getLastPage();

                            if (response.body().getData().getTotal() != 0) {
                                favouritepostsDataList.addAll(response.body().getData().getData());
                                favouritePostsAdapter.notifyDataSetChanged();
                            } else {
                                setError(getString(R.string.no_posts));
                            }

                        }

                    } catch (Exception e) {

                    }
                }

                @Override
                public void onFailure(Call<GetAllPosts> call, Throwable t) {
                    try {
                        notificationsFragmentSFlShimmerDonations.stopShimmer();
                        notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                        loadMore.setVisibility(View.GONE);
                        donationsListFragmentSrRefreshDonations.setRefreshing(false);
                        setError(getString(R.string.error_list));
                    } catch (Exception e) {

                    }
                }
            });

        } else {
            try {
                notificationsFragmentSFlShimmerDonations.stopShimmer();
                notificationsFragmentSFlShimmerDonations.setVisibility(View.GONE);
                loadMore.setVisibility(View.GONE);
                donationsListFragmentSrRefreshDonations.setRefreshing(false);
                setError(getString(R.string.error_inter_net));
            } catch (Exception e) {

            }

        }
    }

    private void setError(String errorTitleTxt) {
        if (favouritepostsDataList.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reInit();

                        getFavouritePosts(1);

                }
            };
            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.group49
                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

    @OnClick(R.id.more_my_favourite_img_back_recent)
    public void onViewClicked() {
    }
}