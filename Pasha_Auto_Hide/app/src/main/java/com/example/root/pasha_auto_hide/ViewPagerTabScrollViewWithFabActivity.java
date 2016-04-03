package com.example.root.pasha_auto_hide;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public class ViewPagerTabScrollViewWithFabActivity extends ViewPagerTabScrollViewActivity {

    @Override
    protected NavigationAdapter newViewPagerAdapter() {
        return new NavigationAdapter(getSupportFragmentManager());
    }

    private static class NavigationAdapter extends ViewPagerTabScrollViewActivity.NavigationAdapter {
        public NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        protected Fragment newFragment() {
            return new ViewPagerTabScrollViewWithFabFragment();
        }
    }
}