package com.ccjeng.stock.model;

import org.json.JSONObject;

/**
 * Created by andycheng on 2016/3/4.
 */
public class SearchAutocompleteItem {
    public static final int MAX_ORDER = 10;
    public static final int MIN_ORDER = 0;
    public String name;
    public String symbol;
    public String exchange;
    public int order;

    public SearchAutocompleteItem(String name, String symbol, String exchange) {
        try {
            this.name = name;
            this.symbol = symbol;
            this.exchange = exchange;
            this.order = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
