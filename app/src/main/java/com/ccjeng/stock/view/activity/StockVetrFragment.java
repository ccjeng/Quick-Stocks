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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;

//import com.github.mikephil.charting.data.PieEntry;

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

    public StockVetrFragment(View view, Context context, StockQuote currentStock) {

        this.view = view;
        this.context = context;
        this.currentStock = currentStock;

        sector = (TextView) view.findViewById(R.id.sector);
        industry = (TextView) view.findViewById(R.id.industry);
        ratingBar = (RatingBar)  view.findViewById(R.id.ratingBar);
        detailsColumn = (ListView) view.findViewById(R.id.vetrDetails);
        pieChart = (PieChart) view.findViewById(R.id.piechart);
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
                //setStrongPieChart(securityInfo);
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
        pieChart.getLegend().setEnabled(false);

        //set data
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        yVals.add(new Entry(securityInfo.getData().getAggratingcalc().getTotalSellRatings(),0));
        yVals.add(new Entry(securityInfo.getData().getAggratingcalc().getTotalHoldRatings(),1));
        yVals.add(new Entry(securityInfo.getData().getAggratingcalc().getTotalBuyRatings(),2));

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Sell");
        xVals.add("Hold");
        xVals.add("Buy");

        PieDataSet dataSet = new PieDataSet(yVals, "Crowd Rating");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(new int[] {R.color.price_red, R.color.company_name_gray, R.color.price_green}, context);
        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        //data.setValueTypeface(mTfLight);
        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();

    }


}
