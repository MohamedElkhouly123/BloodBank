package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.example.bloodbank.view.fragment.BaSeFragment;
import com.example.bloodbank.view.fragment.homeCycle2.more.MoreFragment;
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
import static com.example.bloodbank.utils.GeneralRequest.getData;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;
import static com.example.bloodbank.utils.RecycleTool.setRecycleTool;
import static com.example.bloodbank.utils.network.InternetState.isConnected;

public class ArticlesFragment extends BaSeFragment {


    @BindView(R.id.home_articles_categories_id_spinner)
    Spinner homeArticlesCategoriesIdSpinner;
    @BindView(R.id.home_articles_categories_etxt)
    EditText homeArticlesCategoriesEtxt;
    @BindView(R.id.home_articles_recycler_view)
    RecyclerView homeArticlesRecyclerView;
    @BindView(R.id.home_articles_add_floating_action_button)
    FloatingActionButton homeArticlesAddFloatingActionButton;
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

    private BottomNavigationView navBar;
    private LinearLayoutManager linearLayoutManager;
    private ClientData clientData;
    private EmptySpinnerAdapter categoriesAdapter;
    public List<PostsData> postsDataList = new ArrayList<>();
    public PostsAndFavouritesAdapter postsAdapter;
    private int maxPage = 0;
    private OnEndLess onEndLess;
    private boolean PostsFilter = false;
    private String keyword = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_articles, container, false);
        ButterKnife.bind(this, root);
        navBar = getActivity().findViewById(R.id.nav_view);
        clientData = LoadUserData(getActivity());
        categoriesAdapter = new EmptySpinnerAdapter(getActivity());
        getData(getApiClient().getCategories(), categoriesAdapter, "mbbjkkb", homeArticlesCategoriesIdSpinner);

        init();

        setUpActivity();

        return root;
    }

    private void init() {
        linearLayoutManager = new LinearLayoutManager(getActivity());
        homeArticlesRecyclerView.setLayoutManager(linearLayoutManager);

        onEndLess = new OnEndLess(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        loadMore.setVisibility(View.VISIBLE);

                        if (PostsFilter) {
                            onFilter(current_page);
                        } else {
                            getPosts(current_page);
                        }
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                }

            }
        };
        homeArticlesRecyclerView.addOnScrollListener(onEndLess);

        postsAdapter = new PostsAndFavouritesAdapter(getContext(),getActivity(), postsDataList, false);
        homeArticlesRecyclerView.setAdapter(postsAdapter);

        if (PostsFilter) {
            onFilter(1);
        } else {
            getPosts(1);
        }
        donationsListFragmentSrRefreshDonations.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reInit();
                if (PostsFilter) {
                    onFilter(1);
                } else {
                    getPosts(1);
                }
            }
        });
    }

    private void onFilter(int page) {
        keyword = homeArticlesCategoriesEtxt.getText().toString().trim();

        PostsFilter = true;
        Call<GetAllPosts> call;
        call = getApiClient().postsFilter(clientData.getApiToken(), keyword, page,categoriesAdapter.selectedId);

        call(call, page);
    }

    private void getPosts(int page) {
        Call<GetAllPosts> call;


        call = getApiClient().getAllPosts(clientData.getApiToken(), page);

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
                                postsDataList.addAll(response.body().getData().getData());
                                postsAdapter.notifyDataSetChanged();
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

    private void reInit() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        postsDataList = new ArrayList<>();
        postsAdapter = new PostsAndFavouritesAdapter(getContext(),getActivity(), postsDataList, false);
        homeArticlesRecyclerView.setAdapter(postsAdapter);
    }

    private void setError(String errorTitleTxt) {
        if (postsDataList.size() == 0) {
            View.OnClickListener action = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reInit();
                    if (PostsFilter) {
                        onFilter(1);
                    } else {
                        getPosts(1);
                    }
                }
            };
            setRecycleTool(baseActivity, errorSubView, errorImage, errorTitle, errorAction, R.drawable.group49
                    , errorTitleTxt, getString(R.string.reload), action);
        }
    }

    @OnClick({R.id.home_articles_categories_etxt, R.id.home_articles_add_floating_action_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_articles_categories_etxt:
                break;
            case R.id.home_articles_add_floating_action_button:
                replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram, new AddDonationRequestFragment());
                navBar.setVisibility(View.GONE);

                break;
        }
    }

    @OnClick(R.id.home_articles_search_btn)
    public void onViewClicked() {
        if (categoriesAdapter.selectedId == 0 && keyword == "" && PostsFilter) {
            PostsFilter = false;
            getPosts(1);
        } else {
            onFilter(1);
        }
    }

    @Override
    public void onBack() {

        replaceFragment(getActivity().getSupportFragmentManager(), R.id.user_activity_fram
                , new MoreFragment());
    }


}