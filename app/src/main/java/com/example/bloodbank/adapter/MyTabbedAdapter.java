package com.example.bloodbank.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bloodbank.view.fragment.homeCycle2.home.ArticlesFragment;
import com.example.bloodbank.view.fragment.homeCycle2.home.DonationRequestsFragment;
import com.example.bloodbank.view.fragment.homeCycle2.home.HomeFragment;

public class MyTabbedAdapter extends FragmentPagerAdapter {
    HomeFragment context;
    int totalTabs;
    public MyTabbedAdapter(HomeFragment c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ArticlesFragment article  = new ArticlesFragment();
                return article;
            case 1:
                DonationRequestsFragment donationRequests = new DonationRequestsFragment();
                return donationRequests;

            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}