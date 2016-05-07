package com.ccjeng.stock.controller;

import android.util.Log;

import com.ccjeng.stock.Stock;
import com.ccjeng.stock.controller.rss.RSSHandler;
import com.ccjeng.stock.model.interfaces.GoogleFinanceService;
import com.ccjeng.stock.model.interfaces.INewsCallback;
import com.ccjeng.stock.model.rss.RSSFeed;
import com.ccjeng.stock.utils.Constant;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsAPI {

    private static final String TAG = "RSSService";

    private String stocksSymbol;

    public NewsAPI(String stocksSymbol){
        this.stocksSymbol = stocksSymbol;
    }

    public void requestRSS(final INewsCallback callback) throws IOException {

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
                .baseUrl(Constant.ENDPOINT_GOOGLE)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okhttpClient)
                .build();

        GoogleFinanceService googleFinanceService = retrofit.create(GoogleFinanceService.class);

        googleFinanceService.getNews(stocksSymbol)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {

                        RSSFeed rssFeed;

                        //Standard RSS Parser
                        InputSource inputSource = null;
                        try {
                            inputSource = new InputSource();
                            inputSource.setEncoding("ISO-8859-1");
                            inputSource.setCharacterStream(new StringReader(responseBody.string().trim()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        try {

                            //RSS
                            SAXParserFactory spf = SAXParserFactory.newInstance();
                            SAXParser sp = spf.newSAXParser();
                            XMLReader xr = sp.getXMLReader();
                            RSSHandler mRSSHandler = new RSSHandler();

                            xr.setContentHandler(mRSSHandler);
                            try {
                                xr.parse(inputSource);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            rssFeed = mRSSHandler.getParsedData();
                            callback.onRSSReceived(rssFeed);


                        } catch (ParserConfigurationException e) {
                            e.printStackTrace();
                            Log.d(TAG, "ParserConfigurationException error = " + e.toString());

                        } catch (SAXException e) {
                            e.printStackTrace();
                            Log.d(TAG, "SAXException error = " + e.toString());
                        }
                    }
                });

    }


}