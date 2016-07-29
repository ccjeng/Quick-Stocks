package com.ccjeng.stock.utils;

/**
 * Created by andycheng on 2016/3/13.
 */
public class Constant {

    public final static String ENDPOINT_MOD = "http://dev.markitondemand.com/MODApis/Api/v2/";
    public final static String ENDPOINT_YQL = "http://query.yahooapis.com/v1/public/";
    public final static String ENDPOINT_YAHOO_CHART = "http://chartapi.finance.yahoo.com/instrument/1.0/";
    public static final String ENDPOINT_YAHOO_NEWS = "https://feeds.finance.yahoo.com/rss/2.0/";
    public final static String ENDPOINT_GOOGLE = "http://www.google.com/finance/";
    public final static String ENDPOINT_CUSTOM = "http://stockapi-ccjeng.rhcloud.com/";


    public static enum GraphicType {
        YEAR5, YEAR, MONTH6, MONTH3, MONTH, DAY5, DAY
    }

}
