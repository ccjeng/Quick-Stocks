package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.model.historicaldata.Quote;
import com.ccjeng.stock.model.interfaces.IHistoricalDataCallback;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.view.DetailActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by andycheng on 2016/3/13.
 */
public class ChartDataAPI {

    private static final String TAG = "ChartDataAPI";

    private String stocksSymbol;
    private ArrayList<Quote> historicalDataItems;
    private DetailActivity.GraphicType graphicType;

    public ChartDataAPI(String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.historicalDataItems = new ArrayList<Quote>();
    }

    public void getChartData() {

        final String URL  = Constant.ENDPOINT_YAHOO_CHART + stocksSymbol +"/chartdata;type=quote;range=1d/csv";
        Log.d(TAG, URL);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    parser(response.body().string());
                  //  throw new IOException("Unexpected code " + response);
                }
            }
        });


    }


    private void parser(String text) {

        try {
            InputStream stream = new ByteArrayInputStream(text.getBytes());
            InputStreamReader isr = new InputStreamReader(stream,"UTF-8");
            BufferedReader br = new BufferedReader(isr);
            Boolean beginFind = false;
            String s;

            Log.d(TAG, br.readLine());
/*
            while (null != (s = br.readLine())) {

                if (s.trim().startsWith("volume")) {
                    beginFind = true;
                } else
                    beginFind = false;
                }
                if (beginFind) {
                    Log.d(TAG, s.trim());
                   // sb.append(s.trim());
                }

            }*/

            br.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    private String buildQuotesGetQuery() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(c.getTime());
        switch (graphicType) {
            case WEEK: {
                c.add(Calendar.WEEK_OF_MONTH, -1);
                break;
            }
            case MONTH: {
                c.add(Calendar.MONTH, -1);
                break;
            }
            case YEAR: {
                c.add(Calendar.YEAR, -1);
                break;
            }
        }
        String maxDate = sdf.format(c.getTime());

        String query = "select * from yahoo.finance.historicaldata where symbol = '" + stocksSymbol + "' and startDate = '" +
                maxDate + "' and endDate = '" + todayDate + "'";
        return query;
    }


}
