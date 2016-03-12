package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.CompanyLookup;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by andycheng on 2016/3/7.
 */
public interface CompanyLookupService {

    @GET("Lookup/json?")
    Observable<List<CompanyLookup>> getCompany(@Query("input") String input);

}
