package com.example.bloodbank.utils;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.example.bloodbank.adapter.EmptySpinnerAdapter;
import com.example.bloodbank.data.model.generalResponse.GeneralResponse;
import com.example.bloodbank.data.model.generalResponse.GeneralResponseData2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.bloodbank.utils.HelperMethod.showToast;

public class GeneralRequest {
    public static void getData(Call<GeneralResponse> call, final EmptySpinnerAdapter adapter, final String hint, Spinner spinner, Activity activity) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        String msg= response.errorBody().toString();
                        showToast(activity,msg);
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }


        });
    }
    public static void getData(Call<GeneralResponse> call, EmptySpinnerAdapter adapter, String hint, Spinner spinner, AdapterView.OnItemSelectedListener listener) {
        call.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(listener);
                    }
                } catch (Exception e) {
                }
            }
            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {
            }
        });
    }
}