package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.google.StockQuote;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andycheng on 2016/3/15.
 */
public interface GoogleFinanceService {

    @GET("info?infotype=infoquoteall")
    Observable<ArrayList<StockQuote>> getStockQuotes(@Query("q") String query);

    @GET("info?client=ig")
    Observable<ArrayList<StockQuote>> getStockQuotesIG(@Query("q") String query);

    @GET("q/{symbols}")
    Observable<ArrayList<StockQuote>> getStockQuoteList(@Path("symbols") String query);

    @GET("company_news?output=rss")
    Observable<ResponseBody> getNews(@Query("q") String symbol);


}
