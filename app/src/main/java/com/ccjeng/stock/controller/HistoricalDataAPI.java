package com.ccjeng.stock.controller;

import android.graphics.Color;
import android.util.Log;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.historicaldata.HistoricalData;
import com.ccjeng.stock.model.historicaldata.Quote;
import com.ccjeng.stock.model.interfaces.YahooStockService;
import com.ccjeng.stock.view.DetailActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by andycheng on 2016/3/12.
 */
public class HistoricalDataAPI {

    private static final String TAG = "HistoricalDataAPI";

    private DetailActivity context;
    private LineChart chart;
    private String stocksSymbol;
    private ArrayList<String> dateValues;
    private ArrayList<Float> closeValues;
    private ArrayList<Float> highValues;
    private ArrayList<Float> lowValues;
    private ArrayList<Float> volumeValues;


    private DetailActivity.GraphicType graphicType;

    private static final int GRAPHIC_FILL_ALPHA = 230;
    private static final float GRAPHIC_CUBIC_INTENSITY = 0.5f;
    private static final float GRAPHIC_LINE_WIDTH = 2f;


    public HistoricalDataAPI(DetailActivity context, String stocksSymbol, DetailActivity.GraphicType graphicType) {
        this.stocksSymbol = stocksSymbol;
        this.graphicType = graphicType;
        this.dateValues = new ArrayList<String>();
        this.closeValues = new ArrayList<Float>();
        this.highValues = new ArrayList<Float>();
        this.lowValues = new ArrayList<Float>();
        this.volumeValues = new ArrayList<Float>();
        this.context = context;
    }

    public void getHistoricalData(){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okhttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        final String ENDPOINT = "http://query.yahooapis.com/v1/public/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient)
                .build();

        YahooStockService yahooStockService = retrofit.create(YahooStockService.class);

        yahooStockService.geHistoricalData(buildQuotesGetQuery())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HistoricalData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Error", e.getMessage());
                    }

                    @Override
                    public void onNext(HistoricalData items) {

                        for (Quote item: items.getQuery().getResults().getQuote()) {
                            dateValues.add(item.getDate());
                            closeValues.add(Float.valueOf(item.getClose()));
                            highValues.add(Float.valueOf(item.getHigh()));
                            lowValues.add(Float.valueOf(item.getLow()));
                            volumeValues.add(Float.valueOf(item.getVolume()));
                        }

                        Collections.reverse(dateValues);
                        Collections.reverse(closeValues);
                        Collections.reverse(highValues);
                        Collections.reverse(lowValues);
                        Collections.reverse(volumeValues);

                        setChart();
                    }
                });

    }

    private void setChart(){


        LineChart mChart = (LineChart) context.findViewById(R.id.chartStock);

        ArrayList<String> xVals = new ArrayList<String>();

        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < closeValues.size(); i++) {
            //xVals.add(String.valueOf(i));
            xVals.add(parseDateFormat(dateValues.get(i), graphicType));
            yVals.add(new Entry(closeValues.get(i), i));
        }

        LineDataSet historicalDataSet = new LineDataSet(yVals, context.currentStock.getName());
        historicalDataSet.setDrawCircles(false);
        historicalDataSet.setDrawCubic(true);
        historicalDataSet.setDrawFilled(false);
        //historicalDataSet.setFillAlpha(GRAPHIC_FILL_ALPHA);
        historicalDataSet.setCubicIntensity(GRAPHIC_CUBIC_INTENSITY);
        historicalDataSet.setLineWidth(GRAPHIC_LINE_WIDTH);
        //historicalDataSet.setFillColor(context.getResources().getColor(R.color.toolbar_orange));
        historicalDataSet.setColor(context.getResources().getColor(R.color.colorPrimary));

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

    private String buildQuotesGetQuery() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = sdf.format(c.getTime());
        switch (graphicType) {
            case WEEK: {
                c.add(Calendar.WEEK_OF_MONTH, -1);
                break;
            }
            case MONTH: {
                c.add(Calendar.MONTH, -1);
                break;
            }
            case YEAR: {
                c.add(Calendar.YEAR, -1);
                break;
            }
        }
        String maxDate = sdf.format(c.getTime());

        String query = "select * from yahoo.finance.historicaldata where symbol = '" + stocksSymbol + "' and startDate = '" +
                maxDate + "' and endDate = '" + todayDate + "'";
        return query;
    }

    private String parseDateFormat(String s, DetailActivity.GraphicType graphicType) {

        String dateValue = "";
        switch (graphicType) {
            case WEEK:
            case MONTH:
                dateValue = s.split("-")[1]+s.split("-")[2];
                break;
            case YEAR:
                dateValue = s.split("-")[1];
                break;
        }

        Log.d(TAG, s + " = " + dateValue);

        return dateValue;
    }

}
