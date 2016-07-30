package com.ccjeng.stock.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.utils.CustomPagerEnum;
import com.ccjeng.stock.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StockMainActivity extends BaseActivity {

    private static final String TAG = "StockMainActivity";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.container) ViewPager mViewPager;
    @Bind(R.id.tabs) TabLayout tabs;


    private static StockQuote currentStock = null;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabs.setupWithViewPager(mViewPager);

        if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_STOCK)) {
            currentStock = (StockQuote) getIntent().getSerializableExtra(MainActivity.INTENT_EXTRA_STOCK);
        }

        //set toolbar title
        getSupportActionBar().setTitle(currentStock.getSymbol());
        getSupportActionBar().setSubtitle(getString(R.string.last_trade) + currentStock.getLastTradeDateTimeLong());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[getArguments().getInt(ARG_SECTION_NUMBER)];

            View rootView = inflater.inflate(customPagerEnum.getLayoutResId(), container, false);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 0:
                    StockInfoFragment stockInfoFragment = new StockInfoFragment(rootView, getActivity(), currentStock);
                    stockInfoFragment.refreshData();
                    break;

                case 1:
                    StockNewsFragment stockNewsFragment = new StockNewsFragment(rootView, getActivity(), currentStock);
                    stockNewsFragment.getNews();
                    break;
            }

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return CustomPagerEnum.values().length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CustomPagerEnum customPagerEnum = CustomPagerEnum.values()[position];
            return getString(customPagerEnum.getTitleResId());
        }
    }

}
