package com.ccjeng.stock;

import android.app.Application;

import com.ccjeng.stock.utils.PreferencesManager;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by andycheng on 2016/3/10.
 */
public class Stock extends Application {
    private static final String ROBOTO_FONT_PATH = "fonts/Roboto-Regular.ttf";
    public static final boolean APPDEBUG = BuildConfig.DEBUG;
    private Tracker mTracker;

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

    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            if (APPDEBUG) {
                analytics.getInstance(this).setDryRun(true);
            }
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);

        }
        return mTracker;
    }
}
