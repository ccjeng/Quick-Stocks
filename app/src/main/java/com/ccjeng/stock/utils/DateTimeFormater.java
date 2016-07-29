package com.ccjeng.stock.utils;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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


    public static String parseDateFormat(String s, Constant.GraphicType graphicType) {

        String dateValue = s;

        switch (graphicType) {
            case DAY:
                dateValue = convertDateTime(s);
                break;
            case DAY5:
                dateValue = convertDateTime(s).substring(0, 4);
                break;
            case MONTH:
            case MONTH3:
            case MONTH6:
                dateValue = s.substring(4, 8);
                break;
            case YEAR:
            case YEAR5:
                dateValue = s.substring(0, 6);
                break;
        }

        //Log.d(TAG, s + " = " + dateValue);

        return dateValue;
    }


    private static String convertDateTime(String timestamp) {

        Date date = new Date(Long.valueOf(timestamp)* 1000);
        DateTimeFormatter df = DateTimeFormat.forPattern("MMdd HH:mm");
        DateTimeZone timeZone = DateTimeZone.forID( "America/New_York" );
        DateTime dt = new DateTime( date, timeZone );

        return dt.toString(df);
    }

}
