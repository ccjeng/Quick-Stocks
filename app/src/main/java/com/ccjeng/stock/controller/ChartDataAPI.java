package com.ccjeng.stock.controller;

import android.content.Context;
import android.util.Log;

import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.interfaces.IChartDataCallback;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.view.DetailActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;


/**
 * Created by andycheng on 2016/3/13.
 */
public class ChartDataAPI {

    private static final String TAG = "ChartDataAPI";

    private String stocksSymbol;
    private ArrayList<HistoricalDataItem> historicalDataItems;
    private DetailActivity.GraphicType graphicType;
    private DetailActivity context;

    public ChartDataAPI(DetailActivity context, String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.context = context;
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.historicalDataItems = new ArrayList<HistoricalDataItem>();
    }

    public void getChartData(final IChartDataCallback callback) {

        final String URL  = Constant.ENDPOINT_YAHOO_CHART + stocksSymbol +"/chartdata;type=quote;range="+ getRange() + "/csv";
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

                    // Run view-related code back on the main thread
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onQueryReceived(historicalDataItems);
                        }
                    });



                }
            }
        });


    }

    /**
     * Parse csv string to historical data, then put them in the ArrayList
     * @param text csv string to be parsed
     */
    private void parser(String text) {
        InputStream stream = new ByteArrayInputStream(text.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        try {

            Boolean beginFind = false;
            String line;

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("volume:")) {
                    beginFind = true;
                }

                if (beginFind) {
                    //Log.d(TAG, line);

                    if (!line.startsWith("volume:")) {
                        historicalDataItems.add(new HistoricalDataItem(
                                line.split(",")[0],
                                line.split(",")[1],
                                line.split(",")[2],
                                line.split(",")[3],
                                line.split(",")[4],
                                line.split(",")[5]
                        ));
                    }
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error while closing input stream: "+e);
            }
        }
    }


    private String getRange() {
        String range = "";
        switch (graphicType) {
            case DAY:
                range = "1d";
                break;
            case DAY5:
                range = "5d";
                break;
            case MONTH:
                range = "1m";
                break;
            case MONTH3:
                range = "3m";
                break;
            case MONTH6:
                range = "6m";
                break;
            case YEAR:
                range = "1y";
                break;
            case YEAR5:
                range = "5y";
                break;
        }

        return range;
    }


}
