package com.example.bloodbank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.model.listOfCities.GeneralResponseData;

import java.util.ArrayList;
import java.util.List;

public class EmptySpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<GeneralResponseData> generalResponseDataList = new ArrayList<>();
    private LayoutInflater inflter;
    public int selectedId = 0;

    public EmptySpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        this.generalResponseDataList = generalResponseDataList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseData> generalResponseDataList, String hint) {
        generalResponseDataList.add(new GeneralResponseData(0, hint));
        this.generalResponseDataList = generalResponseDataList;
    }

    @Override
    public int getCount() {
        return generalResponseDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_list_item_1, null);

        TextView names = (TextView) view.findViewById(R.id.txt_spinner);

        names.setText(generalResponseDataList.get(i).getName());
        selectedId = generalResponseDataList.get(i).getId();

        return view;
    }
    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.simple_list_item_1, null);

        TextView text = (TextView) view.findViewById(R.id.txt_spinner);
        text.setText(generalResponseDataList.get(i).getName());
        text.setTextColor(Color.parseColor("#9A0B0B"));
        selectedId = generalResponseDataList.get(i).getId();

        return view;

    }

}
