package com.example.bloodbank.view.fragment.homeCycle2.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.bloodbank.R;
import com.example.bloodbank.view.fragment.BaSeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends BaSeFragment {


    @BindView(R.id.more_contact_us_img_back_recent)
    ImageView imgBackRecent;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.moe_contact_us_phone_txt)
    TextView moeContactUsPhoneTxt;
    @BindView(R.id.moe_contact_us_email_txt)
    TextView moeContactUsEmailTxt;
    @BindView(R.id.moe_contact_us_message_title_etxt)
    EditText moeContactUsMessageTitleEtxt;
    @BindView(R.id.moe_contact_us_the_message_etxt)
    EditText moeContactUsTheMessageEtxt;
    @BindView(R.id.moe_contact_us_send_btn)
    Button moeContactUsSendBtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_more_contact_us, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @OnClick({R.id.more_contact_us_img_back_recent, R.id.moe_contact_us_send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.more_contact_us_img_back_recent:
                break;
            case R.id.moe_contact_us_send_btn:
                break;
        }
    }
}