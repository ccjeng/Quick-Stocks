package com.ccjeng.stock.model.interfaces;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by andycheng on 2016/3/21.
 */
public interface YahooFinanceService {

    @GET("{symbol}/chartdata;type=quote;range={range}/csv")
    Observable<ResponseBody> getChart(@Path("symbol") String symbol, @Path("range") String range);

}
