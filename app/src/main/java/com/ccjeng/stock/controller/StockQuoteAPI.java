package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.Stock;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.GoogleFinanceService;
import com.ccjeng.stock.model.interfaces.IStockQuoteCallback;
import com.ccjeng.stock.utils.Constant;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andycheng on 2016/3/11.
 */
public class StockQuoteAPI {

    private static final String TAG = "StockQuoteAPI";
    private String[] stocksSymbols;
    private ArrayList<StockQuote> stockItems;

    public StockQuoteAPI(String[] stocksSymbols) {
        this.stocksSymbols = stocksSymbols;
        this.stockItems = new ArrayList<StockQuote>();
    }

    public void getStockQuote(final IStockQuoteCallback callback) {
        try {

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
                    .baseUrl(Constant.ENDPOINT_CUSTOM)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClient)
                    .build();

            GoogleFinanceService googleStockService = retrofit.create(GoogleFinanceService.class);
            googleStockService.getStockQuoteList(buildQuotesGetQuery())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ArrayList<StockQuote>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG, "Error = " + e.getMessage());
                        }

                        @Override
                        public void onNext(ArrayList<StockQuote> stockQuotes) {

                            for(StockQuote item: stockQuotes) {
                                Log.d(TAG, item.getName());
                                callback.onQueryReceived(stockQuotes);
                            }

                            //callback.onQueryReceived(stockQuote);

                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String buildQuotesGetQuery() {
        String query = ""; // = "select * from yahoo.finance.quotes where symbol in (";
        for (int i = 0; i < stocksSymbols.length; i++) {
            query += stocksSymbols[i];//"'" + stocksSymbols[i] + "'";
            if (i != stocksSymbols.length - 1) {
                query += ",";
            }
        }

        //query += ")";
        return query;
    }


}
