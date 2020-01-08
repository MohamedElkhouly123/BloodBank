package com.example.bloodbank.view.fragment.homeCycle2.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.posts.getAllPosts.PostsData;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.utils.HelperMethod.onLoadImageFromUrl;

public class ArticleItemDetailsFragment extends BaSeFragment {

    public PostsData postsData;
    @BindView(R.id.fragment_home_article_item_details_img_back_recent)
    ImageView fragmentHomeArticleItemDetailsImgBackRecent;
    @BindView(R.id.fragment_home_article_item_details_safe_ways_and_favourte_txt)
    TextView fragmentHomeArticleItemDetailsSafeWaysAndFavourteTxt;
    @BindView(R.id.post_details_fragment_iv_is_favourite)
    ImageView postDetailsFragmentIvIsFavourite;
    @BindView(R.id.fragment_home_article_item_details_safe_ways_txt1)
    TextView fragmentHomeArticleItemDetailsSafeWaysTxt1;
    @BindView(R.id.post_details_fragment_iv_photo)
    ImageView postDetailsFragmentIvPhoto;
    private ClientData client;

    public ArticleItemDetailsFragment() {
        // Required empty public constructor
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home_article_item_details, container, false);
        ButterKnife.bind(this, root);
        client = LoadUserData(getActivity());
        getPostDetails();
        setUpActivity();
        return root;
    }

    @OnClick(R.id.fragment_home_article_item_details_img_back_recent)
    public void onViewClicked() {
    }

    private void getPostDetails() {
        try {
            onLoadImageFromUrl(postDetailsFragmentIvPhoto, postsData.getThumbnailFullPath(), getActivity());
            fragmentHomeArticleItemDetailsSafeWaysAndFavourteTxt.setText(postsData.getTitle());
            fragmentHomeArticleItemDetailsSafeWaysTxt1.setText(postsData.getContent());

            if (postsData.getIsFavourite()) {
                postDetailsFragmentIvIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);

            } else {
                postDetailsFragmentIvIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            }
        } catch (Exception e) {

        }

    }
}