package com.example.bloodbank.view.fragment.homeCycle2.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavuoritesFragment extends BaSeFragment {


    @BindView(R.id.more_my_favourite_img_back_recent)
    ImageView moreMyFavouriteImgBackRecent;
    @BindView(R.id.more_my_favourite_recycler_view)
    RecyclerView moreMyFavouriteRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_favourite, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.more_my_favourite_img_back_recent)
    public void onViewClicked() {
    }
}