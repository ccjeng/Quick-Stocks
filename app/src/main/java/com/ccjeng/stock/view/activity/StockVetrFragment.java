package com.ccjeng.stock.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.VetrAPI;
import com.ccjeng.stock.model.VetrAggratingcalcItem;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.ISecurityInfoCallback;
import com.ccjeng.stock.model.vetr.SecurityInfo;
import com.ccjeng.stock.view.adapter.VetrDetailsAdapter;

/**
 * Created by andycheng on 2016/8/30.
 */
public class StockVetrFragment {

    private View view;
    private Context context;
    private StockQuote currentStock;


    private TextView sector;
    private TextView industry;
    private ListView detailsColumn;

    public StockVetrFragment(View view, Context context, StockQuote currentStock) {

        this.view = view;
        this.context = context;
        this.currentStock = currentStock;

        sector = (TextView) view.findViewById(R.id.sector);
        industry = (TextView) view.findViewById(R.id.industry);
        detailsColumn = (ListView) view.findViewById(R.id.vetrDetails);

    }

    public void getSecurityInfo() {

        ISecurityInfoCallback gotSecurityInfoCallback = new ISecurityInfoCallback() {
            @Override
            public void onQueryReceived(SecurityInfo securityInfo) {

                sector.setText(securityInfo.getData().getSecurity().getSector());
                industry.setText(securityInfo.getData().getSecurity().getIndustry());

                //
                detailsColumn.setEnabled(false);
                VetrDetailsAdapter adapter = new VetrDetailsAdapter(context, VetrAggratingcalcItem.Column(context, securityInfo.getData().getAggratingcalc()));
                detailsColumn.setAdapter(adapter);
            }
        };

        VetrAPI vetrAPI = new VetrAPI(currentStock.getSymbol());
        vetrAPI.getSecurityInfo(gotSecurityInfoCallback);

    }
}
