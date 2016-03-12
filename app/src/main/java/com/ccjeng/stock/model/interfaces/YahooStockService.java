package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.historicaldata.HistoricalData;
import com.ccjeng.stock.model.quotes.StockQuotes;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andycheng on 2016/3/11.
 */
public interface YahooStockService {

    @GET("yql?format=json&env=store://datatables.org/alltableswithkeys")
    Observable<StockQuotes> getStockQuotes(@Query("q") String query);

    @GET("yql?format=json&env=store://datatables.org/alltableswithkeys")
    Observable<HistoricalData> geHistoricalData(@Query("q") String query);


}
