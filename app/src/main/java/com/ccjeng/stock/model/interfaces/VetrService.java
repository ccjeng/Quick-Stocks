package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.vetr.SecurityInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andycheng on 2016/8/30.
 */
public interface VetrService {

    @GET("research/securityInfo?")
    Observable<SecurityInfo> getSecurityInfo(@Query("ticker") String ticker, @Query("apikey") String apikey);

}
