package com.example.bloodbank.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.bloodbank.R;
import com.example.bloodbank.data.model.client.ClientData;
import com.example.bloodbank.data.model.donations.DonationRequests.GeneralDonationRequestData;
import com.example.bloodbank.view.activity.BaseActivity;
import com.example.bloodbank.view.fragment.homeCycle2.home.DonorDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.bloodbank.data.local.SharedPreferencesManger.LoadUserData;
import static com.example.bloodbank.utils.HelperMethod.onPermission;
import static com.example.bloodbank.utils.HelperMethod.replaceFragment;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.ViewHolder> {

    private Context context;
    private BaseActivity activity;
    private List<GeneralDonationRequestData> donationDataList = new ArrayList<>();
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private ClientData clientData;
    private String lang;

    public DonationAdapter(Activity activity, List<GeneralDonationRequestData> restaurantDataList) {
        this.context = activity;
        this.activity = (BaseActivity) activity;
        this.donationDataList = restaurantDataList;
        viewBinderHelper.setOpenOnlyOne(true);
        clientData = LoadUserData(activity);
        lang = "eg";
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_request_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setSwipe(holder, position);
    }

    @SuppressLint("SetTextI18n")
    private void setData(ViewHolder holder, int position) {

        holder.position = position;
        holder.donationAdapterTvName.setText(activity.getString(R.string.patient_name) + " : " +
                donationDataList.get(position).getClient().getName());

        holder.donationAdapterTvAddress.setText(activity.getString(R.string.hospital) + " : " +
                donationDataList.get(position).getHospitalAddress());

        holder.donationAdapterTvCity.setText(activity.getString(R.string.city) + " : " +
                donationDataList.get(position).getCity().getName());

        holder.donationAdapterTvBloodType.setText(donationDataList.get(position).getBloodType().getName());

    }

    private void setSwipe(final ViewHolder holder, final int position) {
        holder.donationAdapterPsIvDonationSwipeLayout.computeScroll();
        if (lang.equals("ar")) {
            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_LEFT);
        } else {
            holder.donationAdapterPsIvDonationSwipeLayout.setDragEdge(SwipeRevealLayout.DRAG_EDGE_RIGHT);
        }

        viewBinderHelper.bind(holder.donationAdapterPsIvDonationSwipeLayout, String.valueOf(donationDataList.get(position).getId()));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewBinderHelper.openLayout(String.valueOf(donationDataList.get(position).getId()));
                holder.donationAdapterPsIvDonationSwipeLayout.computeScroll();
            }
        });

    }

    @Override
    public int getItemCount() {
        return donationDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.donation_request_item_donation_name_txt)
        TextView donationAdapterTvName;
        @BindView(R.id.donation_request_item_hospital_name_txt)
        TextView donationAdapterTvAddress;
        @BindView(R.id.donation_request_item_city_name_txt)
        TextView donationAdapterTvCity;
        @BindView(R.id.donation_request_item_blood_type_txt)
        TextView donationAdapterTvBloodType;
        @BindView(R.id.donation_request_item_swlay)
        SwipeRevealLayout donationAdapterPsIvDonationSwipeLayout;

        private View view;
        private int position;

        private ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }

        @OnClick({R.id.donation_request_item_edit_floating_btn, R.id.donation_request_item_phone_floating_btn})
        public void onViewClicked(View view) {
            switch (view.getId()) {
                case R.id.donation_request_item_edit_floating_btn:

                    DonorDetailsFragment donationDetailsFragment = new DonorDetailsFragment();
                    donationDetailsFragment.donationId = donationDataList.get(position).getId();
                    replaceFragment(activity.getSupportFragmentManager(), R.id.home_activity_fram, donationDetailsFragment);

                    break;
                case R.id.donation_request_item_phone_floating_btn:
                    onPermission(activity);

                    activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", donationDataList.get(position).getPhone(), null)));
                    break;
            }
        }
    }
}
