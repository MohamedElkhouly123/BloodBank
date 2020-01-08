package com.example.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.bloodbank.data.model.generalResponse.GeneralResponseData2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationSettingAdapter extends RecyclerView.Adapter<NotificationSettingAdapter.ViewHolder> {


    private Context context;
    private Activity activity;
    private List<String> oldBloodTypes = new ArrayList<>();
    private List<GeneralResponseData2> bloods = new ArrayList<>();
    public List<Integer> ids = new ArrayList<>();

    public NotificationSettingAdapter(Context context, Activity activity, List<GeneralResponseData2> bloods, List<String> oldBloodTypes) {
        this.context = context;
        this.activity = activity;
        this.bloods = bloods;
        this.oldBloodTypes = oldBloodTypes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_box_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        try {

            holder.checkboxItemChbx.setChecked(false);
            for (int i = 0; i < oldBloodTypes.size(); i++) {
                if (oldBloodTypes.get(i).equals(String.valueOf(bloods.get(position).getId()))) {
                    holder.checkboxItemChbx.setChecked(true);
                    ids.add(bloods.get(position).getId());
                    break;
                }
            }

            holder.checkboxItemChbx.setText(bloods.get(position).getName());

        } catch (Exception e) {

        }
    }

    private void setAction(ViewHolder holder, final int position) {

        try {

            holder.checkboxItemChbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        ids.add(bloods.get(position).getId());
                    } else {
                        for (int i = 0; i < ids.size(); i++) {
                            if (ids.get(i).equals(bloods.get(position).getId())) {
                                ids.remove(i);
                            }
                        }
                    }
                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {
        return bloods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.checkbox_item_chbx)
        CheckBox checkboxItemChbx;
        private View view;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
