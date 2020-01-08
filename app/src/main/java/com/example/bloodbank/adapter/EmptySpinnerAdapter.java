package com.example.bloodbank.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bloodbank.R;
import com.example.bloodbank.data.model.generalResponse.GeneralResponseData2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmptySpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<GeneralResponseData2> generalResponseDataList = new ArrayList<>();
    private LayoutInflater inflter;
    public int selectedId = 0;

    public EmptySpinnerAdapter(Context applicationContext) {
        this.context = applicationContext;
        this.generalResponseDataList = generalResponseDataList;
        inflter = (LayoutInflater.from(applicationContext));
    }

    public void setData(Collection<? extends GeneralResponseData2> generalResponseDataList, String hint) {
        this.generalResponseDataList = new ArrayList<>();
        this.generalResponseDataList.add(new GeneralResponseData2(0, hint));
        this.generalResponseDataList.addAll(generalResponseDataList);
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
