package com.ccjeng.stock.model;

import android.content.Context;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.quotes.Quote;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/12.
 */
public class StockDetailsItem {
    public String title;
    public String value;

    public StockDetailsItem(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public static ArrayList<StockDetailsItem> fromDefaulrLeftColumn(Context mContext, Quote stock) {
        ArrayList<StockDetailsItem> allItems = new ArrayList<StockDetailsItem>();
        allItems.add(new StockDetailsItem(mContext.getString(R.string.open), stock.getOpen()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.volume), stock.getVolume()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.days_high), stock.getDaysHigh()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.days_low), stock.getDaysLow()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.year_high), stock.getYearHigh()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.year_low), stock.getYearLow()));
        return allItems;
    }

    public static ArrayList<StockDetailsItem> fromDefaulrRightColumn(Context mContext, Quote stock) {
        ArrayList<StockDetailsItem> allItems = new ArrayList<StockDetailsItem>();
        allItems.add(new StockDetailsItem(mContext.getString(R.string.prev_close), stock.getPreviousClose()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.ask), stock.getAsk()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.bid), stock.getBid()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.pe_ratio), stock.getPERatio()));
        // allItems.add(new StockDetailsItem(mContext.getString(R.string.exchange), stock.getStockExchange()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.eps), stock.getEarningsShare()));
        allItems.add(new StockDetailsItem(mContext.getString(R.string.market_capital), stock.getMarketCapitalization()));

        return allItems;
    }
}
