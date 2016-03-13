package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.model.interfaces.IStockQuoteCallback;
import com.ccjeng.stock.model.interfaces.YahooStockService;
import com.ccjeng.stock.model.FinanceItem;
import com.ccjeng.stock.model.quotes.Quote;
import com.ccjeng.stock.model.quotes.StockQuotes;

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
    private ArrayList<Quote> stockItems;
   // private ArrayList<FinanceItem> financeItems;

    public StockQuoteAPI(String[] stocksSymbols) {
        this.stocksSymbols = stocksSymbols;
        this.stockItems = new ArrayList<Quote>();
       // this.financeItems = new ArrayList<FinanceItem>();
    }


    public void getStockQuote(final IStockQuoteCallback callback) {
        try {

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

            yahooStockService.getStockQuotes(buildQuotesGetQuery())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<StockQuotes>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.d("Error", e.getMessage());
                            }

                            @Override
                            public void onNext(StockQuotes stockQuotes) {

                                for(Quote quote: stockQuotes.getQuery().getResults().getQuote()) {
                                    Log.d(TAG, quote.getSymbol() + " - " + quote.getLastTradePriceOnly());
                                    stockItems.add(quote);
                                }

                                callback.onQueryReceived(stockItems);

                            }
                        });



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String buildQuotesGetQuery() {
        String query = "select * from yahoo.finance.quotes where symbol in (";
        for (int i = 0; i < stocksSymbols.length; i++) {
            query += "'" + stocksSymbols[i] + "'";
            if (i != stocksSymbols.length - 1) {
                query += ",";
            }
        }
        query += ")";
        return query;
    }
}
