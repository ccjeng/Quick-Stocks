package com.ccjeng.stock.view.activity;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.ccjeng.stock.R;

/**
 * Created by andycheng on 2016/3/27.
 */
public class SettingFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);


    }

}
