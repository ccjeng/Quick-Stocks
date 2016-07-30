package com.ccjeng.stock.view.activity;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.NewsAPI;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.INewsCallback;
import com.ccjeng.stock.model.rss.RSSFeed;
import com.ccjeng.stock.view.adapter.NewsRSSAdapter;

import java.io.IOException;

/**
 * Created by andycheng on 2016/7/29.
 */
public class StockNewsFragment {

    private static final String TAG = "StockNewsFragment";

    private View view;
    private Context context;
    private StockQuote currentStock;
    private RecyclerView lvNews;

    public StockNewsFragment(View view, Context context, StockQuote currentStock) {

        this.view = view;
        this.context = context;
        this.currentStock = currentStock;

        lvNews = (RecyclerView) view.findViewById(R.id.lvNews);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        lvNews.setLayoutManager(llm);
        lvNews.setHasFixedSize(true);
        lvNews.setItemAnimator(new DefaultItemAnimator());

    }

    public void getNews() {
        INewsCallback callback = new INewsCallback() {
            @Override
            public void onRSSReceived(final RSSFeed rssFeed) {

                NewsRSSAdapter adapter = new NewsRSSAdapter(context, rssFeed);
                lvNews.setAdapter(adapter);
            }
        };

        try {

            NewsAPI srv = new NewsAPI(currentStock.getSymbol());
            srv.requestRSS(callback);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d(TAG, "getFeed error = " + e.toString());
        }
    }

}
