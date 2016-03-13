package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.quotes.Quote;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/13.
 */
public interface IStockQuoteCallback {

    public void onQueryReceived(ArrayList<Quote> stockItems);

}
