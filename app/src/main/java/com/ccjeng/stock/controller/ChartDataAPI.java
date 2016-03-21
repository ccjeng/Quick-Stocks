package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.Stock;
import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.interfaces.IChartDataCallback;
import com.ccjeng.stock.model.interfaces.YahooFinanceService;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.view.DetailActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by andycheng on 2016/3/13.
 */
public class ChartDataAPI {

    private static final String TAG = "ChartDataAPI";

    private String stocksSymbol;
    private ArrayList<HistoricalDataItem> historicalDataItems;
    private long sumVolume;
    private DetailActivity.GraphicType graphicType;

    public ChartDataAPI(String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.historicalDataItems = new ArrayList<HistoricalDataItem>();
    }

    public void getChartData(final IChartDataCallback callback) {


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (Stock.APPDEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        }

        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.ENDPOINT_YAHOO_CHART)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okhttpClient)
                .build();

        YahooFinanceService yahooFinanceService = retrofit.create(YahooFinanceService.class);

        yahooFinanceService.getChart(stocksSymbol, getRange())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            parser(responseBody.string());
                            callback.onQueryReceived(historicalDataItems, withSuffix(sumVolume));

                        } catch (IOException e) {
                            e.printStackTrace();
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

            sumVolume = 0;

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
                        if (graphicType.equals(DetailActivity.GraphicType.DAY)) {
                            sumVolume = sumVolume + Integer.valueOf(line.split(",")[5]);
                        }
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

    private static String withSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        return String.format(Locale.US, "%.1f%c",
                count / Math.pow(1000, exp),
                "kMGTPE".charAt(exp-1));
    }

}
