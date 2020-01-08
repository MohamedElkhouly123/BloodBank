package com.example.bloodbank.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.notifications.getNotificationList.GetNotificationListData;
import com.example.bloodbank.view.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.bloodbank.utils.GetTimeAgo.getDate;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private BaseActivity activity;
    private List<GetNotificationListData> notificationDataList = new ArrayList<>();
    private String lang = "en";

    public NotificationAdapter(Activity activity, List<GetNotificationListData> notificationDataList) {
        this.activity = (BaseActivity) activity;
        this.notificationDataList = notificationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.cardview_notifications_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        if (notificationDataList.get(position).getPivot().getIsRead().equals("0")) {
            holder.notificationItemNottifyImg.setImageResource(R.drawable.ic_notifications_black_24dp);
        } else {
            holder.notificationItemNottifyImg.setImageResource(R.drawable.ic_notifications_none_black_24dp);
        }

        holder.notificationItemNottifyTxt.setText(notificationDataList.get(position).getTitle());

        holder.notificationItemDateTxt.setText(getDate(notificationDataList.get(position).getUpdatedAt(), new Locale(lang)));

    }

    private void setAction(ViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_item_nottify_img)
        ImageView notificationItemNottifyImg;
        @BindView(R.id.notification_item_nottify_txt)
        TextView notificationItemNottifyTxt;
        @BindView(R.id.notification_item_date_txt)
        TextView notificationItemDateTxt;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
