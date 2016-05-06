package com.ccjeng.stock.utils;

import android.util.Log;

import org.joda.time.DateTimeZone;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andycheng on 2015/12/24.
 */
public class DateTimeFormater {
    private static final String TAG = "DateTimeFormater";
    public static String parse(String datetime){

        String strDT = "";
        if(datetime == null){
            return "";
        }

        String dateFromatFrom = "EEE,dd MMM yyyy HH:mm:ss zzz";
        String dateFromatTo = "yyyy-MM-dd HH:mm";
       // String timeZone = "GMT+8";
        SimpleDateFormat sdfFrom = new SimpleDateFormat(dateFromatFrom, Locale.US);
        SimpleDateFormat sdfTo = new SimpleDateFormat(dateFromatTo, Locale.US);

        sdfFrom.setTimeZone(DateTimeZone.getDefault().toTimeZone());

        try {
            //if not valid, it will throw ParseException
            Date date = sdfFrom.parse(datetime);
            strDT = sdfTo.format(date);
            //Log.d(TAG, datetime +"--" +strDT);
            return strDT;

        } catch (ParseException e) {
            Log.d(TAG, "ParseException=" + e.toString());
            Log.d(TAG, datetime);

            //        e.printStackTrace();
            return datetime;
        }

        //return strDT;
    }
}
