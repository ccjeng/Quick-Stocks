package com.ccjeng.stock.utils;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/10.
 */

public class PreferencesManager {

    public static final String PREF_STOCKS_LIST = "stocksList";

    private static PreferencesManager preferencesManager;
    private TinyDB tinyDB;


    public static PreferencesManager getInstance() {
        if (preferencesManager == null) {
            preferencesManager = new PreferencesManager();
        }
        return preferencesManager;
    }

    public void init(Context appContext) {
        tinyDB = new TinyDB(appContext);
    }

    public void addStockSymbolToPrefs(String stockSymbol) {
        ArrayList<String> stocksList = tinyDB.getListString(PREF_STOCKS_LIST);
        stocksList.add(stockSymbol);
        tinyDB.putListString(PREF_STOCKS_LIST, stocksList);
    }

    public void removeStockSymbolFromPrefs(String stockSymbol) {
        ArrayList<String> stocksList = tinyDB.getListString(PREF_STOCKS_LIST);
        stocksList.remove(stockSymbol);
        tinyDB.putListString(PREF_STOCKS_LIST, stocksList);
    }

    public boolean stocksSetContains(String stockSymbol) {
        ArrayList<String> stocksList = tinyDB.getListString(PREF_STOCKS_LIST);
        return stocksList.contains(stockSymbol);
    }

    public void saveStockList(ArrayList<String> stocksList) {
        tinyDB.putListString(PREF_STOCKS_LIST, stocksList);
    }

    public void saveBoolean(String key, boolean value) {
        tinyDB.putBoolean(key, value);
    }

    public boolean getBoolean(String key) {
        return tinyDB.getBoolean(key);
    }

    public boolean hasBoolean(String key) {
        return tinyDB.contains(key);
    }

    public ArrayList<String> getStockList() {
        return tinyDB.getListString(PREF_STOCKS_LIST);
    }


    public boolean contains(String key) {
        return tinyDB.contains(key);
    }
}