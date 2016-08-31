package com.ccjeng.stock.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

//import com.daimajia.androidanimations.library.Techniques;
//import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Alon on 14.03.2015.
 */
public class GlobalUtils {

    // time in miliseconds
    public static final int URL_CONNECTION_TIME_OUT = 15000;
    public static final int INPUT_STREAM_READ_TIME_OUT = 10000;

    private static final String GOOGLE_PLAY_URL = "https://play.google.com/store/apps/details?id=";

    /**
     * Gets json response from remote server by url
     */
    public static JSONObject getJsonWithUrl(String url) throws IOException, JSONException {

        URL link = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) link.openConnection();
        connection.setReadTimeout(INPUT_STREAM_READ_TIME_OUT);
        connection.setConnectTimeout(URL_CONNECTION_TIME_OUT);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        connection.disconnect();
        String result = sb.toString();
        JSONObject jsonObject = new JSONObject(result);
        return jsonObject;
    }


    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public static String buildGooglePlayLink(Context mContext) {
        return GOOGLE_PLAY_URL + mContext.getPackageName();
    }

    public static boolean isNetworkConnected(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            // There are no active networks.
            return false;
        } else
            return true;
    }

    public static void showErrorSnackBar(View view, int message) {

        Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundColor(Color.RED);

        TextView tv = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);

        snackbar.show();

    }
    /*
    public static void safeAnimate(View view, int duration, Techniques type) {
        if (view != null) {
            YoYo.with(type)
                    .duration(duration)
                    .playOn(view);
        }
    }*/


    public static Constant.GraphicType getChartPeroid(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String chartPeriod =  prefs.getString("chart_period", "1D");
        switch (chartPeriod) {
            case "1D":
                return Constant.GraphicType.DAY;
            case "5D":
                return Constant.GraphicType.DAY5;
            case "1M":
                return Constant.GraphicType.MONTH;
            case "3M":
                return Constant.GraphicType.MONTH3;
            case "6M":
                return Constant.GraphicType.MONTH6;
            case "1Y":
                return Constant.GraphicType.YEAR;
            case "5Y":
                return Constant.GraphicType.YEAR5;
            default:
                return Constant.GraphicType.DAY;
        }

    }


    public static String NumberToString(Double input) {
        DecimalFormat df = new DecimalFormat("##.00");
        return String.valueOf(df.format(input));
    }
}
