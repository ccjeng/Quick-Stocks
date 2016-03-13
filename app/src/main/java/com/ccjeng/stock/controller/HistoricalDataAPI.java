package com.ccjeng.stock.controller;

import android.graphics.Color;
import android.util.Log;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.historicaldata.HistoricalData;
import com.ccjeng.stock.model.historicaldata.Quote;
import com.ccjeng.stock.model.interfaces.IHistoricalDataCallback;
import com.ccjeng.stock.model.interfaces.YahooStockService;
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


    public HistoricalDataAPI(String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.historicalDataItems = new ArrayList<Quote>();
    }

    public void getHistoricalData(final IHistoricalDataCallback callback){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        final String ENDPOINT = "http://query.yahooapis.com/v1/public/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
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
