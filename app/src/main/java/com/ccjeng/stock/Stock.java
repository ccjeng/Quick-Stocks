package com.ccjeng.stock;

import android.app.Application;

import com.ccjeng.stock.utils.PreferencesManager;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by andycheng on 2016/3/10.
 */
public class Stock extends Application {
    private static final String ROBOTO_FONT_PATH = "fonts/Roboto-Regular.ttf";
    public static final boolean APPDEBUG = BuildConfig.DEBUG;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(ROBOTO_FONT_PATH)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        PreferencesManager.getInstance().init(getApplicationContext());
        if (!PreferencesManager.getInstance().contains(PreferencesManager.PREF_STOCKS_LIST)) {
            ArrayList<String> stocksList = new ArrayList<String>();
            stocksList.add("AAPL");
            stocksList.add("GOOG");
            stocksList.add("YHOO");
            PreferencesManager.getInstance().saveStockList(stocksList);
        }
    }
}
