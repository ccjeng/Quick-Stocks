package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.Stock;
import com.ccjeng.stock.controller.CleanGsonConverter.CleanGsonConverterFactory;
import com.ccjeng.stock.model.interfaces.ISecurityInfoCallback;
import com.ccjeng.stock.model.interfaces.VetrService;
import com.ccjeng.stock.model.vetr.SecurityInfo;
import com.ccjeng.stock.utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andycheng on 2016/8/30.
 */
public class VetrAPI {
    private static final String TAG = "VetrAPI";
    private String ticker;

    public VetrAPI(String ticker) {
        this.ticker = ticker;
    }
    public void getSecurityInfo(final ISecurityInfoCallback callback) {

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
                .baseUrl(Constant.ENDPOINT_VETR)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(CleanGsonConverterFactory.create())
                .client(okhttpClient)
                .build();

        VetrService vetrService = retrofit.create(VetrService.class);
        vetrService.getSecurityInfo(ticker,"zayqqb54DcaYLzpSAjufuSskWPkyRs8M")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<SecurityInfo>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error = " + e.getMessage());
                    }

                    @Override
                    public void onNext(SecurityInfo securityInfo) {
                        callback.onQueryReceived(securityInfo);

                    }
                });


    }
}
