package com.ccjeng.stock.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.ChartDataAPI;
import com.ccjeng.stock.controller.StockDetailsAdapter;
import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.StockDetailsItem;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.IChartDataCallback;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.utils.DateTimeFormater;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andycheng on 2016/7/29.
 */
public class StockInfoFragment {
    private static final String TAG = "StockInfoFragment";

    View view;

    private NestedScrollView scrollView;
    private TextView tvGraphicLabelDay;
    private TextView tvGraphicLabelMonth6;
    private TextView tvGraphicLabelMonth3;
    private TextView tvGraphicLabelMonth;
    private TextView tvGraphicLabelYear;
    private TextView tvGraphicLabelYear5;
    private TextView tvGraphicLabelDay5;
    private TextView tvStockName;
    private TextView tvStockSymbol;
    private TextView tvStockPriceChange;
    private TextView tvStockPrice;
    private TextView tvPreStockTime;
    private TextView tvPreStockPriceChange;
    private View viewPriceIndicator;
    private ListView lvLeftDetailsColumn;
    private ListView lvRightDetailsColumn;
    private ProgressWheel progressWheel;
    private CombinedChart mChart;
    private BarChart mBarChart;
    private Constant.GraphicType currentGraphicType;
    private StockQuote currentStock;
    private Context context;

    private static final int GRAPHIC_FILL_ALPHA = 230;
    private static final float GRAPHIC_CUBIC_INTENSITY = 0.1f;
    private static final float GRAPHIC_LINE_WIDTH = 1f;


    public StockInfoFragment(View view, Context context, StockQuote currentStock) {
        this.view = view;
        this.currentStock = currentStock;
        this.context = context;

        scrollView = (NestedScrollView) view.findViewById(R.id.scrollview);
        tvGraphicLabelDay = (TextView) view.findViewById(R.id.tvGraphicLabelDay);
        tvGraphicLabelMonth6 = (TextView) view.findViewById(R.id.tvGraphicLabelMonth6);
        tvGraphicLabelMonth3 = (TextView) view.findViewById(R.id.tvGraphicLabelMonth3);
        tvGraphicLabelMonth = (TextView) view.findViewById(R.id.tvGraphicLabelMonth);
        tvGraphicLabelYear = (TextView) view.findViewById(R.id.tvGraphicLabelYear);
        tvGraphicLabelYear5 = (TextView) view.findViewById(R.id.tvGraphicLabelYear5);
        tvGraphicLabelDay5 = (TextView) view.findViewById(R.id.tvGraphicLabelDay5);
        tvStockName = (TextView) view.findViewById(R.id.tvStockName);
        tvStockSymbol = (TextView) view.findViewById(R.id.tvStockSymbol);
        tvStockPrice = (TextView) view.findViewById(R.id.tvStockPrice);
        tvStockPriceChange = (TextView) view.findViewById(R.id.tvStockPriceChange);
        tvPreStockTime = (TextView) view.findViewById(R.id.tvPreStockTime);
        tvPreStockPriceChange = (TextView) view.findViewById(R.id.tvPreStockPriceChange);
        viewPriceIndicator = (View) view.findViewById(R.id.viewPriceIndicator);
        lvLeftDetailsColumn = (ListView) view.findViewById(R.id.lvStockDetailsLeft);
        lvRightDetailsColumn = (ListView) view.findViewById(R.id.lvStockDetailsRight);
        progressWheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
        mChart = (CombinedChart) view.findViewById(R.id.chartStock);
        mBarChart = (BarChart) view.findViewById(R.id.barchartStock);
    }


    public void refreshData() {
        progressWheel.setVisibility(View.VISIBLE);
        setCurrentStock(currentStock);
        getChartData();
        scrollView.smoothScrollTo(0, 0);

    }


    private void setCurrentStock(StockQuote currentStock){

        tvStockName.setText(currentStock.getName());
        tvStockSymbol.setText(currentStock.getSymbol());
        tvStockPrice.setText(currentStock.getLastTradePrice());
        tvStockPriceChange.setText(currentStock.getFormatedPriceChange());
        tvStockPriceChange.setTextColor(currentStock.getPriceColor(context));
        viewPriceIndicator.setBackgroundColor(currentStock.getPriceColor(context));

        //PreMarket Data
        if (!currentStock.getAfterHourTime().equals("") && !currentStock.getAfterHourChange().equals("0.00")) {
            tvPreStockTime.setVisibility(View.VISIBLE);
            tvPreStockPriceChange.setVisibility(View.VISIBLE);
            String preMarketTime = "Pre Market: "
                    + currentStock.getAfterHourTime().split(",")[1].replace("EDT","") + "  "
                    + currentStock.getAfterHourLastTradePrice() + "  ";

            tvPreStockTime.setText(preMarketTime);
            tvPreStockPriceChange.setText(currentStock.getFormatedPreMarketPriceChange());
            tvPreStockPriceChange.setTextColor(currentStock.getPricePreMarketColor(context));
        } else {
            tvPreStockTime.setVisibility(View.GONE);
            tvPreStockPriceChange.setVisibility(View.GONE);
        }

        lvLeftDetailsColumn.setEnabled(false);
        StockDetailsAdapter leftAdapter = new StockDetailsAdapter(context, StockDetailsItem.fromDefaulrLeftColumn(context, currentStock, "-"));
        lvLeftDetailsColumn.setAdapter(leftAdapter);

        lvRightDetailsColumn.setEnabled(false);
        StockDetailsAdapter rightAdapter = new StockDetailsAdapter(context, StockDetailsItem.fromDefaulrRightColumn(context, currentStock));
        lvRightDetailsColumn.setAdapter(rightAdapter);

        currentGraphicType = Constant.GraphicType.DAY;

    }


    private void getChartData() {

        IChartDataCallback gotChartDataCallback = new IChartDataCallback() {
            @Override
            public void onQueryReceived(ArrayList<HistoricalDataItem> items, String volume) {
                setChart(items);
                setBarChart(items);

                if (currentGraphicType.equals(Constant.GraphicType.DAY)) {
                    lvLeftDetailsColumn.setEnabled(false);
                    StockDetailsAdapter leftAdapter = new StockDetailsAdapter(context,
                            StockDetailsItem.fromDefaulrLeftColumn(context, currentStock, volume));
                    lvLeftDetailsColumn.setAdapter(leftAdapter);
                }
            }
        };

        ChartDataAPI chartDataAPI = new ChartDataAPI(currentStock.getSymbol(), currentGraphicType);
        chartDataAPI.getChartData(gotChartDataCallback);

        progressWheel.setVisibility(View.GONE);

    }

    private void setChart(ArrayList<HistoricalDataItem> stockItems){

        ArrayList<Float> closeValues = new ArrayList<Float>();;
        //ArrayList<Float> volumeValues = new ArrayList<Float>();;

        //XAxis
        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < stockItems.size(); i++) {
            xVals.add(DateTimeFormater.parseDateFormat(stockItems.get(i).getDate(), currentGraphicType));
            closeValues.add(Float.valueOf(stockItems.get(i).getClose()));
            //volumeValues.add(Float.valueOf(stockItems.get(i).getVolume()));
        }

        mChart.getAxisLeft().setEnabled(false);
        mChart.getAxisRight().setEnabled(true);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawBorders(false);
        mChart.setDragEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");

        // draw bars behind lines
        mChart.setDrawOrder(new CombinedChart.DrawOrder[] {
                CombinedChart.DrawOrder.BAR,  CombinedChart.DrawOrder.CANDLE, CombinedChart.DrawOrder.LINE
        });

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        leftAxis.removeAllLimitLines();
        leftAxis.setAxisMaxValue(Collections.max(closeValues));
        leftAxis.setAxisMinValue(Collections.min(closeValues));

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        Legend l = mChart.getLegend();
        l.setEnabled(false);

        CombinedData data = new CombinedData(xVals);

        data.setData(generateLineData(stockItems));
        //data.setData(generateCandleData(stockItems));
        mChart.animateX(1000);
        mChart.setData(data);
        mChart.invalidate();


    }

    private LineData generateLineData(ArrayList<HistoricalDataItem> stockItems) {
        LineData d = new LineData();
        ArrayList<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < stockItems.size(); i++) {
            entries.add(new Entry(Float.valueOf(stockItems.get(i).getClose()), i));
        }

        LineDataSet set = new LineDataSet(entries, currentStock.getName());
        set.setDrawCircles(false);
        set.setDrawCubic(true);
        set.setDrawFilled(false);
        //historicalDataSet.setFillAlpha(GRAPHIC_FILL_ALPHA);
        set.setCubicIntensity(GRAPHIC_CUBIC_INTENSITY);
        set.setLineWidth(GRAPHIC_LINE_WIDTH);
        set.setColor(context.getResources().getColor(R.color.colorPrimary));

        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        d.addDataSet(set);

        return d;
    }

    private void setBarChart(ArrayList<HistoricalDataItem> stockItems){
        //Bar Chart
        mBarChart.getAxisRight().setEnabled(false);
        mBarChart.getAxisLeft().setEnabled(false);
        mBarChart.getXAxis().setEnabled(false);
        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(false);
        mBarChart.setDescription("");
        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);
        mBarChart.setDrawGridBackground(false);
        mBarChart.setMaxVisibleValueCount(1);

        //XAxis xAxis = mBarChart.getXAxis();
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);

        YAxis leftAxis = mBarChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        Legend l = mBarChart.getLegend();
        l.setEnabled(false);

        //XAxis
        ArrayList<String> xVals = new ArrayList<String>();
        //YAxis
        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < stockItems.size(); i++) {
            xVals.add(DateTimeFormater.parseDateFormat(stockItems.get(i).getDate(), currentGraphicType));
            entries.add(new BarEntry(Float.valueOf(stockItems.get(i).getVolume()), i));
        }

        BarDataSet dataset = new BarDataSet(entries, "Volume");
        dataset.setColor(context.getResources().getColor(R.color.colorPrimaryDark));
        BarData barData = new BarData(xVals);
        barData.addDataSet(dataset);
        mBarChart.animateX(1000);
        mBarChart.setData(barData);
        mBarChart.invalidate();

    }


    public void onClickGraphicLabel(View v) {

        tvGraphicLabelDay.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth3.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth6.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelDay5.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelYear.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelYear5.setTextColor(context.getResources().getColor(R.color.sliding_menu_text_color));

        tvGraphicLabelDay.setTextSize(14);
        tvGraphicLabelMonth6.setTextSize(14);
        tvGraphicLabelMonth3.setTextSize(14);
        tvGraphicLabelMonth.setTextSize(14);
        tvGraphicLabelDay5.setTextSize(14);
        tvGraphicLabelYear.setTextSize(14);
        tvGraphicLabelYear5.setTextSize(14);

        tvGraphicLabelDay.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelMonth.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelMonth3.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelMonth6.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelDay5.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelYear.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelYear5.setTypeface(null, Typeface.NORMAL);

        ((TextView) v).setTypeface(null, Typeface.BOLD);
        ((TextView) v).setTextSize(16);
        ((TextView) v).setTextColor(context.getResources().getColor(R.color.symbol_black));

        switch (v.getId()) {
            case R.id.tvGraphicLabelDay: {
                currentGraphicType = Constant.GraphicType.DAY;
                break;
            }
            case R.id.tvGraphicLabelMonth6: {
                currentGraphicType = Constant.GraphicType.MONTH6;
                break;
            }
            case R.id.tvGraphicLabelMonth3: {
                currentGraphicType = Constant.GraphicType.MONTH3;
                break;
            }
            case R.id.tvGraphicLabelMonth: {
                currentGraphicType = Constant.GraphicType.MONTH;
                break;
            }
            case R.id.tvGraphicLabelDay5: {
                currentGraphicType = Constant.GraphicType.DAY5;
                break;
            }
            case R.id.tvGraphicLabelYear: {
                currentGraphicType = Constant.GraphicType.YEAR;
                break;
            }
            case R.id.tvGraphicLabelYear5: {
                currentGraphicType = Constant.GraphicType.YEAR5;
                break;
            }

        }

        getChartData();

    }
}
