package com.mobile.app.rentnepal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;

import com.mobile.app.rentnepal.HFragment.HOME;
import com.mobile.app.rentnepal.HFragment.SEARCH;

public class TabLayout extends AppCompatActivity {
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        String[] values = {"Home", "Search", "Post Ad"};

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new HOME();
                case 1:
                    return new SEARCH();
                case 2:
                    return new PostAd();
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

























    /*public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tab_layout, container, false);
        return v;
    }
}*/