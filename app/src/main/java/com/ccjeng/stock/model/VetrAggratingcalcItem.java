package com.ccjeng.stock.model;

import android.content.Context;

import com.ccjeng.stock.model.vetr.Aggratingcalc;

import java.text.DecimalFormat;
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

        DecimalFormat df = new DecimalFormat("##.00");

        allItems.add(new VetrAggratingcalcItem("Current Price", String.valueOf(aggratingcalc.getCurrentPrice())));
        allItems.add(new VetrAggratingcalcItem("Crowd Target Price", String.valueOf(df.format(aggratingcalc.getAvgTarget()))));
        allItems.add(new VetrAggratingcalcItem("%", String.valueOf(df.format(100*aggratingcalc.getAvgTargetPct()))));
        allItems.add(new VetrAggratingcalcItem("Analyst Target Price", String.valueOf(aggratingcalc.getArnTargetPrice())));
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
