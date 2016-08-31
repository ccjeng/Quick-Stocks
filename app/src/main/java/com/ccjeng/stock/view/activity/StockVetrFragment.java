package com.ccjeng.stock.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.VetrAPI;
import com.ccjeng.stock.model.VetrAggratingcalcItem;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.ISecurityInfoCallback;
import com.ccjeng.stock.model.vetr.SecurityInfo;
import com.ccjeng.stock.view.adapter.VetrDetailsAdapter;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/8/30.
 */
public class StockVetrFragment {

    private View view;
    private Context context;
    private StockQuote currentStock;


    private TextView sector;
    private TextView industry;
    private RatingBar ratingBar;
    private ListView detailsColumn;
    private PieChart pieChart;
    private PieChart pieChart2;


    public StockVetrFragment(View view, Context context, StockQuote currentStock) {

        this.view = view;
        this.context = context;
        this.currentStock = currentStock;

        sector = (TextView) view.findViewById(R.id.sector);
        industry = (TextView) view.findViewById(R.id.industry);
        ratingBar = (RatingBar)  view.findViewById(R.id.ratingBar);
        detailsColumn = (ListView) view.findViewById(R.id.vetrDetails);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
        pieChart2 = (PieChart) view.findViewById(R.id.piechart2);


    }

    public void getSecurityInfo() {

        ISecurityInfoCallback gotSecurityInfoCallback = new ISecurityInfoCallback() {
            @Override
            public void onQueryReceived(SecurityInfo securityInfo) {

                sector.setText(securityInfo.getData().getSecurity().getSector());
                industry.setText(securityInfo.getData().getSecurity().getIndustry());
                ratingBar.setRating(securityInfo.getData().getAggratingcalc().getAggRatingScore());
                //
                detailsColumn.setEnabled(false);
                VetrDetailsAdapter adapter = new VetrDetailsAdapter(context, VetrAggratingcalcItem.Column(context, securityInfo.getData().getAggratingcalc()));
                detailsColumn.setAdapter(adapter);

                setPieChart(securityInfo);
                setStrongPieChart(securityInfo);
            }
        };

        VetrAPI vetrAPI = new VetrAPI(currentStock.getSymbol());
        vetrAPI.getSecurityInfo(gotSecurityInfoCallback);

    }

    private void setPieChart(SecurityInfo securityInfo) {

        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setCenterText("Crowd Rating");

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        //set data
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(securityInfo.getData().getAggratingcalc().getTotalSellRatings(), "Sell"));
        entries.add(new PieEntry(securityInfo.getData().getAggratingcalc().getTotalHoldRatings(), "Hold"));
        entries.add(new PieEntry(securityInfo.getData().getAggratingcalc().getTotalBuyRatings(), "Buy"));
        PieDataSet dataSet = new PieDataSet(entries, "Crowd Rating");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(new int[] {R.color.price_red, R.color.company_name_gray, R.color.price_green}, context);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();

    }


    private void setStrongPieChart(SecurityInfo securityInfo) {
        pieChart2.setUsePercentValues(true);
        pieChart2.setDescription("");
        pieChart2.setExtraOffsets(5, 10, 5, 5);

        pieChart2.setDragDecelerationFrictionCoef(0.95f);

        pieChart2.setCenterText("Strong Buy/Sell");

        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setHoleColor(Color.WHITE);

        pieChart2.setTransparentCircleColor(Color.WHITE);
        pieChart2.setTransparentCircleAlpha(110);

        pieChart2.setHoleRadius(58f);
        pieChart2.setTransparentCircleRadius(61f);

        pieChart2.setDrawCenterText(true);

        pieChart2.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart2.setRotationEnabled(true);
        pieChart2.setHighlightPerTapEnabled(true);

        //set data
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry(securityInfo.getData().getAggratingcalc().getTotalStrongSellRatings(), "Sell"));
        entries.add(new PieEntry(securityInfo.getData().getAggratingcalc().getTotalStrongBuyRatings(), "Buy"));
        PieDataSet dataSet = new PieDataSet(entries, "Strong Buy/Sell");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(new int[] {R.color.price_red, R.color.price_green}, context);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        //data.setValueTypeface(mTfLight);
        pieChart2.setData(data);

        // undo all highlights
        pieChart2.highlightValues(null);

        pieChart2.invalidate();
    }
}
