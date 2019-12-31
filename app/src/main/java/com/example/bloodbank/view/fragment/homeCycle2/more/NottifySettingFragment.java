package com.example.bloodbank.view.fragment.homeCycle2.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NottifySettingFragment extends BaSeFragment {


    @BindView(R.id.more_nottifies_setting_back_img)
    ImageView moreNottifiesSettingBackImg;
    @BindView(R.id.more_nottifies_setting_drop_down_blood_types)
    TextView moreNottifiesSettingDropDownBloodTypes;
    @BindView(R.id.more_nottifies_setting_drop_down_governrates)
    TextView moreNottifiesSettingDropDownGovernrates;
    @BindView(R.id.more_nottifies_setting_drop_down_blood_types_Layout)
    LinearLayout moreNottifiesSettingDropDownBloodTypesLayout;
    @BindView(R.id.more_nottifies_setting_drop_down_governrates_layout)
    LinearLayout moreNottifiesSettingDropDownGovernratesLayout;
    private String dropDownGovernratesFlag = "plus";
    private String dropDownBloodTypesFlag = "plus";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_nottify_setting, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick({R.id.more_nottifies_setting_back_img, R.id.more_nottifies_setting_drop_down_governrates, R.id.more_nottifies_setting_dsave_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_nottifies_setting_back_img:
                break;
            case R.id.more_nottifies_setting_drop_down_governrates:
                if (dropDownGovernratesFlag == "plus") {
                    dropDownGovernratesFlag = "minus";
                    moreNottifiesSettingDropDownGovernratesLayout.setVisibility(View.GONE);
                    moreNottifiesSettingDropDownGovernrates.setBackgroundResource(R.drawable.ic_add_black_24dp);

                } else {
                    dropDownGovernratesFlag = "plus";
                    moreNottifiesSettingDropDownGovernratesLayout.setVisibility(View.VISIBLE);
                    moreNottifiesSettingDropDownGovernrates.setBackgroundResource(R.drawable.ic_remove_black_24dp);
                }

                break;
            case R.id.more_nottifies_setting_dsave_btn:
                break;
        }
    }
}