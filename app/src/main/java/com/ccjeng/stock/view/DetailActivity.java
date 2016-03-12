package com.ccjeng.stock.view;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.HistoricalDataAPI;
import com.ccjeng.stock.controller.StockDetailsAdapter;
import com.ccjeng.stock.model.StockDetailsItem;
import com.ccjeng.stock.model.quotes.Quote;
import com.github.mikephil.charting.charts.LineChart;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    private static final int GRAPHIC_FILL_ALPHA = 230;
    private static final float GRAPHIC_CUBIC_INTENSITY = 0.1f;
    private static final float GRAPHIC_LINE_WIDTH = 1f;

    public static enum GraphicType {
        YEAR, MONTH, WEEK
    }

    public Quote currentStock;
    private GraphicType currentGraphicType;

    //@Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.scrollview) ScrollView scrollView;
    @Bind(R.id.tvGraphicLabelMonth) TextView tvGraphicLabelMonth;
    @Bind(R.id.tvGraphicLabelYear) TextView tvGraphicLabelYear;
    @Bind(R.id.tvGraphicLabelWeek) TextView tvGraphicLabelWeek;
    @Bind(R.id.tvStockName) TextView tvStockName;
    @Bind(R.id.tvStockSymbol) TextView tvStockSymbol;
    @Bind(R.id.tvStockPrice) TextView tvStockPrice;
    @Bind(R.id.tvStockPriceChange) TextView tvStockPriceChange;
    @Bind(R.id.viewPriceIndicator) View viewPriceIndicator;

    @Bind(R.id.lvStockDetailsLeft) ListView lvLeftDetailsColumn;
    @Bind(R.id.lvStockDetailsRight) ListView lvRightDetailsColumn;

    @Bind(R.id.chartStock) LineChart chartStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //setSupportActionBar(toolbar);

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

        currentGraphicType = GraphicType.MONTH;

        HistoricalDataAPI historicalDataAPI = new HistoricalDataAPI(DetailActivity.this, currentStock.getSymbol(), currentGraphicType);
        historicalDataAPI.getHistoricalData();

        //scroll to the top
        scrollView.smoothScrollTo(0, 0);


    }

    public void onClickGraphicLabel(View v) {

        //tvGraphicLabelMonth.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        //tvGraphicLabelWeek.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));
        //tvGraphicLabelYear.setTextColor(getResources().getColor(R.color.sliding_menu_text_color));

        tvGraphicLabelMonth.setTextSize(14);
        tvGraphicLabelWeek.setTextSize(14);
        tvGraphicLabelYear.setTextSize(14);

        tvGraphicLabelMonth.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelWeek.setTypeface(null, Typeface.NORMAL);
        tvGraphicLabelYear.setTypeface(null, Typeface.NORMAL);

        ((TextView) v).setTypeface(null, Typeface.BOLD);
        ((TextView) v).setTextSize(16);
        ((TextView) v).setTextColor(getResources().getColor(R.color.symbol_black));

        switch (v.getId()) {
            case R.id.tvGraphicLabelMonth: {
                currentGraphicType = GraphicType.MONTH;
                break;
            }
            case R.id.tvGraphicLabelWeek: {
                currentGraphicType = GraphicType.WEEK;
                break;
            }
            case R.id.tvGraphicLabelYear: {
                currentGraphicType = GraphicType.YEAR;
                break;
            }

        }

        HistoricalDataAPI historicalDataAPI = new HistoricalDataAPI(DetailActivity.this, currentStock.getSymbol(), currentGraphicType);
        historicalDataAPI.getHistoricalData();

    }
}
