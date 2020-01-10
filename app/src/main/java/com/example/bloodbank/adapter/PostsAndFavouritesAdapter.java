package com.example.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.api.ApiServices;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.posts.getAllPosts.PostsData;
import com.example.bloodbank.data.model.posts.postToggleFavourite.PostToggleFavourite;
import com.example.bloodbank.utils.HelperMethod;
import com.example.bloodbank.utils.ToastCreator;
import com.example.bloodbank.view.activity.HomeCycleActivity;
import com.example.bloodbank.view.fragment.homeCycle2.home.ArticleItemDetailsFragment;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.data.api.ApiClient.getApiClient;
import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.utils.HelperMethod.onLoadImageFromUrl;


public class PostsAndFavouritesAdapter extends RecyclerView.Adapter<PostsAndFavouritesAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<PostsData> postsDataList = new ArrayList<>();
    private boolean favourites;
    private ClientData clientData;
    private ApiServices apiService;

    public PostsAndFavouritesAdapter(Activity activity,
                                     List<PostsData> postsDataList,
                                     boolean favourites) {
        this.context = context;
        this.activity = activity;
        this.postsDataList = postsDataList;
        this.favourites = favourites;
        clientData = LoadUserData(activity);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_posts_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {
        try {

            onLoadImageFromUrl(holder.postsItemIvPostPhoto, postsDataList.get(position).getThumbnailFullPath(), context);
            holder.postsItemTvTitle.setText(postsDataList.get(position).getTitle());
            if (postsDataList.get(position).getIsFavourite()) {
                holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            } else {
                holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
            }

        } catch (Exception e) {

        }

    }

    private void setAction(final ViewHolder holder, final int position) {
        holder.postsItemIvIsFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite(holder, position);
            }
        });


        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeCycleActivity navigationActivity = (HomeCycleActivity) activity;
                ArticleItemDetailsFragment postDetails = new ArticleItemDetailsFragment();
                postDetails.postsData = postsDataList.get(position);
                HelperMethod.replaceFragment(navigationActivity.getSupportFragmentManager(), R.id.home_activity_fram, postDetails);

            }
        });

    }
    private void toggleFavourite(final ViewHolder holder, final int position) {
        final PostsData postsData = postsDataList.get(position);

        postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());

        if (postsDataList.get(position).getIsFavourite()) {
            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
            ToastCreator.onCreateSuccessToast(activity,context.getResources().getString(R.string.add_to_favourite));


        } else {
            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);

            ToastCreator.onCreateSuccessToast(activity,context.getResources().getString(R.string.remove_from_favourite));
            if (favourites) {
                postsDataList.remove(position);
                notifyDataSetChanged();
//                if (postsDataList.size() == 0) {
//                    articlesFragmentTvNoItems.setVisibility(View.VISIBLE);
//                }
            }
        }
        Call<PostToggleFavourite> call = getApiClient().getPostToggleFavourite(postsData.getId(), clientData.getApiToken());
        call.enqueue(new Callback<PostToggleFavourite>() {
            @Override
            public void onResponse(Call<PostToggleFavourite> call, Response<PostToggleFavourite> response) {
                try {
                    if (response.body().getStatus() == 1) {

                    } else {
                        postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());
                        if (postsDataList.get(position).getIsFavourite()) {
                            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                            if (favourites) {
                                postsDataList.add(position, postsData);
                                notifyDataSetChanged();
                            }
                        } else {
                            holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        }
                    }

                } catch (Exception e) {

                }

            }

            @Override
            public void onFailure(Call<PostToggleFavourite> call, Throwable t) {
                try {
                    postsDataList.get(position).setIsFavourite(!postsDataList.get(position).getIsFavourite());
                    if (postsDataList.get(position).getIsFavourite()) {
                        holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_black_24dp);
                        if (favourites) {
                            postsDataList.add(position, postsData);
                            notifyDataSetChanged();
                        }
                    } else {
                        holder.postsItemIvIsFavourite.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                    }
                } catch (Exception e) {

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.article_item_img)
        PorterShapeImageView postsItemIvPostPhoto;
        @BindView(R.id.posts_item_tv_title)
        TextView postsItemTvTitle;
        @BindView(R.id.posts_item_iv_is_favourite)
        ImageView postsItemIvIsFavourite;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
