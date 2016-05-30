package com.mobile.app.rentnepal;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.app.rentnepal.HFragment.HOME;
import com.mobile.app.rentnepal.HFragment.SEARCH;

import values.MyAd;


public class HomeFragment extends Fragment {
    ViewPager pager;
    TabLayout layout;
    FloatingActionButton fab;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences pref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        pager = (ViewPager) v.findViewById(R.id.viewPagerHome);


        layout = (TabLayout) v.findViewById(R.id.layouts);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Post New Ad. ?");
                builder.setPositiveButton("CREATE !!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getFragmentManager().beginTransaction().replace(R.id.mainLayout, new PostAd()).commit();
                    }
                });
                builder.show();
            }
        });

        pager.setAdapter(new MyAdapter(getChildFragmentManager()));

        layout.setupWithViewPager(pager);
        layout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(layout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return v;
    }

    private class MyAdapter extends FragmentPagerAdapter {
        String[] values = {"Home", "My Ads", "Search"};
        Fragment f1;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    f1 = new HOME();
                    return f1;

                case 1:
                    f1 = new MyAd();
                    return f1;
                case 2:
                    f1 = new SEARCH();
                    return f1;
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return values[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}