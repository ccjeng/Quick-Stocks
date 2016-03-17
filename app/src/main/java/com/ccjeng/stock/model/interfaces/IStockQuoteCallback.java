package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.google.StockQuote;
import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/13.
 */
public interface IStockQuoteCallback {

    public void onQueryReceived(ArrayList<StockQuote> stockItems);

}
