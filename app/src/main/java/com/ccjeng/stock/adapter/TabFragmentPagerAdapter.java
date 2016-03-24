package com.ccjeng.stock.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.view.TabFragment;

/**
 * Created by andycheng on 2016/3/24.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = { "Details", "News" };

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }


    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(position);
    }
/*
    public View getTabView(int position) {
        // Given you have a custom layout in `res/layout/custom_tab.xml` with a TextView and ImageView
        View v = LayoutInflater.from(context).inflate(R.layout.tab_info, null);
        //TextView tv = (TextView) v.findViewById(R.id.textView);
        //tv.setText(tabTitles[position]);
        //ImageView img = (ImageView) v.findViewById(R.id.imgView);
        //img.setImageResource(imageResId[position]);
        return v;
    }*/
}