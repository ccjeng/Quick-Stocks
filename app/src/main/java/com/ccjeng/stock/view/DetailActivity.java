package com.ccjeng.stock.view;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.ChartDataAPI;
import com.ccjeng.stock.controller.StockDetailsAdapter;
import com.ccjeng.stock.model.HistoricalDataItem;
import com.ccjeng.stock.model.StockDetailsItem;
import com.ccjeng.stock.model.interfaces.IChartDataCallback;
import com.ccjeng.stock.model.quotes.Quote;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private static final int GRAPHIC_FILL_ALPHA = 230;
    private static final float GRAPHIC_CUBIC_INTENSITY = 0.1f;
    private static final float GRAPHIC_LINE_WIDTH = 1f;

    public static enum GraphicType {
        YEAR5, YEAR, MONTH6, MONTH3, MONTH, DAY5, DAY
    }

    public Quote currentStock;
    private GraphicType currentGraphicType;

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.scrollview) ScrollView scrollView;
    @Bind(R.id.tvGraphicLabelDay) TextView tvGraphicLabelDay;
    @Bind(R.id.tvGraphicLabelMonth6) TextView tvGraphicLabelMonth6;
    @Bind(R.id.tvGraphicLabelMonth3) TextView tvGraphicLabelMonth3;
    @Bind(R.id.tvGraphicLabelMonth) TextView tvGraphicLabelMonth;
    @Bind(R.id.tvGraphicLabelYear) TextView tvGraphicLabelYear;
    @Bind(R.id.tvGraphicLabelYear5) TextView tvGraphicLabelYear5;
    @Bind(R.id.tvGraphicLabelDay5) TextView tvGraphicLabelDay5;
    @Bind(R.id.tvStockName) TextView tvStockName;
    @Bind(R.id.tvStockSymbol) TextView tvStockSymbol;
    @Bind(R.id.tvStockPrice) TextView tvStockPrice;
    @Bind(R.id.tvStockPriceChange) TextView tvStockPriceChange;
    @Bind(R.id.viewPriceIndicator) View viewPriceIndicator;

    @Bind(R.id.lvStockDetailsLeft) ListView lvLeftDetailsColumn;
    @Bind(R.id.lvStockDetailsRight) ListView lvRightDetailsColumn;

    @Bind(R.id.chartStock) LineChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (getIntent().hasExtra(MainActivity.INTENT_EXTRA_STOCK)) {
            currentStock = (Quote) getIntent().getSerializableExtra(MainActivity.INTENT_EXTRA_STOCK);
        }

        tvStockName.setText(currentStock.getName());
        tvStockSymbol.setText(currentStock.getSymbol());
        tvStockPrice.setText(currentStock.getLastTradePriceOnly());
        tvStockPriceChange.setText(currentStock.getFormatedPriceChange());
        tvStockPriceChange.setTextColor(currentStock.getPriceColor(this));
        viewPriceIndicator.setBackgroundColor(currentStock.getPriceColor(this));

        lvLeftDetailsColumn.setEnabled(false);
        StockDetailsAdapter leftAdapter = new StockDetailsAdapter(this, StockDetailsItem.fromDefaulrLeftColumn(this, currentStock));
        lvLeftDetailsColumn.setAdapter(leftAdapter);

        lvRightDetailsColumn.setEnabled(false);
        StockDetailsAdapter rightAdapter = new StockDetailsAdapter(this, StockDetailsItem.fromDefaulrRightColumn(this, currentStock));
        lvRightDetailsColumn.setAdapter(rightAdapter);

        currentGraphicType = GraphicType.DAY;

        //set toolbar title
        getSupportActionBar().setTitle(currentStock.getSymbol());
        getSupportActionBar().setSubtitle(getString(R.string.last_trade) + " : " + currentStock.getLastTradeDate() + " "+ currentStock.getLastTradeTime());

        getChartData();


        //scroll to the top
        scrollView.smoothScrollTo(0, 0);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickGraphicLabel(View v) {

        tvGraphicLabelDay.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth3.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelMonth6.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelDay5.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelYear.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        tvGraphicLabelYear5.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));

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
        ((TextView) v).setTextColor(getResources().getColor(R.color.symbol_black));

        switch (v.getId()) {
            case R.id.tvGraphicLabelDay: {
                currentGraphicType = GraphicType.DAY;
                break;
            }
            case R.id.tvGraphicLabelMonth6: {
                currentGraphicType = GraphicType.MONTH6;
                break;
            }
            case R.id.tvGraphicLabelMonth3: {
                currentGraphicType = GraphicType.MONTH3;
                break;
            }
            case R.id.tvGraphicLabelMonth: {
                currentGraphicType = GraphicType.MONTH;
                break;
            }
            case R.id.tvGraphicLabelDay5: {
                currentGraphicType = GraphicType.DAY5;
                break;
            }
            case R.id.tvGraphicLabelYear: {
                currentGraphicType = GraphicType.YEAR;
                break;
            }
            case R.id.tvGraphicLabelYear5: {
                currentGraphicType = GraphicType.YEAR5;
                break;
            }

        }

        getChartData();

    }

    private void getChartData() {

        IChartDataCallback gotChartDataCallback = new IChartDataCallback() {
            @Override
            public void onQueryReceived(ArrayList<HistoricalDataItem> items) {
                setChart(items);
            }
        };

        ChartDataAPI chartDataAPI = new ChartDataAPI(this, currentStock.getSymbol(), currentGraphicType);
        chartDataAPI.getChartData(gotChartDataCallback);
    }


    private void setChart(ArrayList<HistoricalDataItem> stockItems){

        ArrayList<String> xVals = new ArrayList<String>();

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        ArrayList<Float> closeValues = new ArrayList<Float>();;

        for (int i = 0; i < stockItems.size(); i++) {
            xVals.add(parseDateFormat(stockItems.get(i).getDate(), currentGraphicType));
            yVals.add(new Entry(Float.valueOf(stockItems.get(i).getClose()), i));

            closeValues.add(Float.valueOf(stockItems.get(i).getClose()));
        }

        LineDataSet historicalDataSet = new LineDataSet(yVals, currentStock.getName());
        historicalDataSet.setDrawCircles(false);
        historicalDataSet.setDrawCubic(true);
        historicalDataSet.setDrawFilled(false);
        //historicalDataSet.setFillAlpha(GRAPHIC_FILL_ALPHA);
        historicalDataSet.setCubicIntensity(GRAPHIC_CUBIC_INTENSITY);
        historicalDataSet.setLineWidth(GRAPHIC_LINE_WIDTH);
        historicalDataSet.setColor(getResources().getColor(R.color.colorPrimary));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(historicalDataSet);

        LineData graphicLineData = new LineData(xVals, dataSets);
        graphicLineData.setDrawValues(false);

        YAxis mainYAxis = mChart.getAxisLeft();
        mainYAxis.removeAllLimitLines();
        mainYAxis.setAxisMaxValue(Collections.max(closeValues));
        mainYAxis.setAxisMinValue(Collections.min(closeValues));

        mainYAxis.setStartAtZero(false);

        mChart.getAxisRight().setEnabled(false);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawBorders(false);
        mChart.setDragEnabled(false);
        mChart.setTouchEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.setDescription("");
        mChart.setData(graphicLineData);
        mChart.getLegend();
        mChart.invalidate();

    }

    /**
     * parse date format and display in chart
     * @param s datetime string
     * @param graphicType date range for chart display
     * @return
     */
    private String parseDateFormat(String s, DetailActivity.GraphicType graphicType) {

        String dateValue = s;

        switch (graphicType) {
            case DAY:
                dateValue = convertDateTime(s);
                break;
            case DAY5:
                dateValue = convertDateTime(s).substring(0, 4);
                break;
            case MONTH:
            case MONTH3:
            case MONTH6:
                dateValue = s.substring(4, 8);
                break;
            case YEAR:
            case YEAR5:
                dateValue = s.substring(0, 6);
                break;
        }

        //Log.d(TAG, s + " = " + dateValue);

        return dateValue;
    }

    /**
     * convert epoch time to human read date
     * @param timestamp
     * @return datetime
     */
    private String convertDateTime(String timestamp) {

        Date date = new Date(Long.valueOf(timestamp)* 1000);
        DateTimeFormatter df = DateTimeFormat.forPattern("MMdd HH:mm");
        DateTimeZone timeZone = DateTimeZone.forID( "America/New_York" );
        DateTime dt = new DateTime( date, timeZone );

        return dt.toString(df);
    }
}
