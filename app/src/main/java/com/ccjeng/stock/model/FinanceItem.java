package com.ccjeng.stock.model;

import android.content.Context;

import com.ccjeng.stock.R;

import java.io.Serializable;

/**
 * Created by andycheng on 2016/3/10.
 */
public class FinanceItem implements Comparable<FinanceItem>,Serializable {

    public String lastUpdate;
    public String stockExchange;
    public String price;
    public String priceChangePercent;
    public String priceChangeNumber;

   // public String Timestamp;

   // public Double LastPrice;

    //public Double Change;
    //public Double ChangePercent;

    public boolean isRemoveMode;
/*
    public String getFormatedPriceChange() {
        return this.priceChangeNumber + " (" + this.priceChangePercent + ")";
    }

    public int getPriceColor(Context context) {
        return this.priceChangeNumber.contains("-") ? context.getResources().getColor(R.color.price_red) : context.getResources().getColor(R.color.price_green);
    }
*/
    public int compareTo(FinanceItem item) {
        return 0;
    }
}
