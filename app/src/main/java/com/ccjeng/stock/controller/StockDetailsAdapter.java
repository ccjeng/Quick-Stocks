package com.ccjeng.stock.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.StockDetailsItem;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/3/12.
 */
public class StockDetailsAdapter extends ArrayAdapter<StockDetailsItem> {

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvValue;
    }

    public StockDetailsAdapter(Context context, ArrayList<StockDetailsItem> stockDetailsItems) {
        super(context, R.layout.lv_stock_details, stockDetailsItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        StockDetailsItem stockDetailsItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_stock_details, parent, false);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvtiltle);
            viewHolder.tvValue = (TextView) convertView.findViewById(R.id.tvValue);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(stockDetailsItem.title);
        viewHolder.tvValue.setText(stockDetailsItem.value);

        return convertView;
    }
}
