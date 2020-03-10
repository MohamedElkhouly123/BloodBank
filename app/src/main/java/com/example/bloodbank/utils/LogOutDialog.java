package com.example.bloodbank.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.view.activity.UserCycleActivity;
import static com.example.bloodbank.data.local.SharedPreferencesManger.clean;

public class LogOutDialog {
//    private ClientData clientData;

    public void showDialog(final Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.setContentView(R.layout.dialog_sign_out);
        dialog.setCanceledOnTouchOutside(true);
//        TextView text = (TextView) dialog.findViewById(R.id.text);

        TextView dialogImageOk = (TextView) dialog.findViewById(R.id.item_sign_out_dialog_btn_yes);
        dialogImageOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Call
//                clientData = LoadUserData(activity);
                clean(activity);
//                String token=new ClientFireBaseToken().getToken();
//                String apiToken=clientData.getApiToken();

                Intent i = new Intent(activity, UserCycleActivity.class);

                activity.startActivity(i);
                // close this activity
                activity.finish();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        TextView dialogImageNo = (TextView) dialog.findViewById(R.id.item_sign_out_dialog_btn_no);
        dialogImageNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });


        dialog.show();

    }
}
