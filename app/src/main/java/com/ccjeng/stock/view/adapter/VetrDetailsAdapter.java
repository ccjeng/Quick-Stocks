package com.ccjeng.stock.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.VetrAggratingcalcItem;

import java.util.ArrayList;

/**
 * Created by andycheng on 2016/8/30.
 */
public class VetrDetailsAdapter extends ArrayAdapter<VetrAggratingcalcItem> {

    private static class ViewHolder {
        TextView tvTitle;
        TextView tvValue;
    }

    public VetrDetailsAdapter(Context context, ArrayList<VetrAggratingcalcItem> vetrDetailsItems) {
        super(context, R.layout.lv_stock_details, vetrDetailsItems);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VetrAggratingcalcItem vetrDetailsItems = getItem(position);
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
        viewHolder.tvTitle.setText(vetrDetailsItems.title);
        viewHolder.tvValue.setText(vetrDetailsItems.value);

        return convertView;
    }
}
