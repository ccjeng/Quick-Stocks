package com.ccjeng.stock.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ccjeng.stock.R;
import com.ccjeng.stock.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by andycheng on 2016/3/27.
 */
public class SettingActivity extends BaseActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationIcon(R.mipmap.icon_toolbal_arrow_white);
        toolbar.setTitleTextColor(Color.WHITE);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingFragment myPreference = new SettingFragment(); //宣告剛剛做好的PreferenceFragment
        transaction.replace(R.id.content_wrapper, myPreference); //將content內容取代為myPreference
        transaction.commit(); //送出交易

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
