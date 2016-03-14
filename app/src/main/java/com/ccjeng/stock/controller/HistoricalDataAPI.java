package com.ccjeng.stock.controller;

import android.graphics.Color;
import android.util.Log;

import com.ccjeng.stock.R;
import com.ccjeng.stock.Stock;
import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.historicaldata.HistoricalData;
import com.ccjeng.stock.model.historicaldata.Quote;
import com.ccjeng.stock.model.interfaces.IHistoricalDataCallback;
import com.ccjeng.stock.model.interfaces.YahooStockService;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.view.DetailActivity;
import com.github.mikephil.charting.charts.LineChart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andycheng on 2016/3/12.
 */
public class HistoricalDataAPI {

    private static final String TAG = "HistoricalDataAPI";

    private String stocksSymbol;
    private ArrayList<Quote> historicalDataItems;
    private DetailActivity.GraphicType graphicType;

    @Deprecated
    public HistoricalDataAPI(String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.historicalDataItems = new ArrayList<Quote>();
    }

    @Deprecated
    public void getHistoricalData(final IHistoricalDataCallback callback){

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
                .baseUrl(Constant.ENDPOINT_YQL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient)
                .build();

        YahooStockService yahooStockService = retrofit.create(YahooStockService.class);

        yahooStockService.geHistoricalData(buildQuotesGetQuery())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistoricalData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onNext(HistoricalData items) {

                        for (Quote item: items.getQuery().getResults().getQuote()) {
                            historicalDataItems.add(item);
                        }

                        Collections.reverse(historicalDataItems);
                        callback.onQueryReceived(historicalDataItems);
                    }
                });

    }


    @Deprecated
    private String buildQuotesGetQuery() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(c.getTime());
        switch (graphicType) {
            case DAY5: {
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
