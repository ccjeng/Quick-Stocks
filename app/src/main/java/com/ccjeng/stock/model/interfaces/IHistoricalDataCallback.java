package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.historicaldata.Quote;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/13.
 */
public interface IHistoricalDataCallback {

    public void onQueryReceived(ArrayList<Quote> stockItems);

}
