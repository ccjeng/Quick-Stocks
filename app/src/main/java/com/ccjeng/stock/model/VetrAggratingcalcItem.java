package com.ccjeng.stock.model;

import android.content.Context;

import com.ccjeng.stock.model.vetr.Aggratingcalc;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/8/30.
 */
public class VetrAggratingcalcItem {

    public String title;
    public String value;

    public VetrAggratingcalcItem(String title, String value) {
        this.title = title;
        this.value = value;
    }


    public static ArrayList<VetrAggratingcalcItem> Column(Context mContext, Aggratingcalc aggratingcalc) {
        ArrayList<VetrAggratingcalcItem> allItems = new ArrayList<VetrAggratingcalcItem>();

        allItems.add(new VetrAggratingcalcItem("Current Price", aggratingcalc.getCurrentPriceString()));
        allItems.add(new VetrAggratingcalcItem("Crowd Target Price", aggratingcalc.getAvgTargetString() +  " ("+ aggratingcalc.getAvgTargetPctString() +")"));
        allItems.add(new VetrAggratingcalcItem("Analyst Target Price", aggratingcalc.getArnTargetPriceString()));
/*
        allItems.add(new VetrAggratingcalcItem("aggRatingScore", String.valueOf(aggratingcalc.getAggRatingScore())));
        allItems.add(new VetrAggratingcalcItem("bullishCount", String.valueOf(aggratingcalc.getBullishCount())));
        allItems.add(new VetrAggratingcalcItem("bearishCount", String.valueOf(aggratingcalc.getBearishCount())));
        allItems.add(new VetrAggratingcalcItem("totalBuyRatings", String.valueOf(aggratingcalc.getTotalBuyRatings())));
        allItems.add(new VetrAggratingcalcItem("totalSellRatings", String.valueOf(aggratingcalc.getTotalSellRatings())));
        allItems.add(new VetrAggratingcalcItem("totalHoldRatings", String.valueOf(aggratingcalc.getTotalHoldRatings())));
        allItems.add(new VetrAggratingcalcItem("totalStrongBuyRatings", String.valueOf(aggratingcalc.getTotalStrongBuyRatings())));
*/


        return allItems;
    }
}
