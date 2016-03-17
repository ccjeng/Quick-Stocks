package com.ccjeng.stock.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.view.MainActivity;
import com.ccjeng.stock.R;
import com.ccjeng.stock.model.FinanceItem;
import com.ccjeng.stock.utils.PreferencesManager;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by andycheng on 2016/3/10.
 */
public class FinanceItemsAdapter extends ArrayAdapter<FinanceItem> implements Swappable {

    private static final String TAG = "FinanceItemsAdapter";

    private static class ViewHolder {
        TextView tvName;
        TextView tvSymbol;
        TextView tvPrice;
        TextView tvPriceChange;
        TextView tvStockLetter;
        View viewPriceIndicator;
        LinearLayout llRemoveCheckMark;
    }

    private Context context;
    private ArrayList<FinanceItem> financeItems;


    public FinanceItemsAdapter(Context context, ArrayList<FinanceItem> financeItems) {
        super(context, R.layout.lv_main_item, financeItems);
        this.context = context;
        this.financeItems = financeItems;
        clearRemoveModes();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FinanceItem financeItem = getItem(position);

        ViewHolder viewHolder;
        //TODO ActivityMain.mode == ActivityMain.Mode.REMOVE - Bad temporary solution
        if (convertView == null || MainActivity.mode == MainActivity.Mode.REMOVE) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_main_item, parent, false);
            viewHolder.tvSymbol = (TextView) convertView.findViewById(R.id.tvStockSymbol);
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tvStockPrice);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tvStockName);
            viewHolder.tvPriceChange = (TextView) convertView.findViewById(R.id.tvStockPriceChange);
            viewHolder.tvStockLetter = (TextView) convertView.findViewById(R.id.tvStockLetter);
            viewHolder.viewPriceIndicator = convertView.findViewById(R.id.viewPriceIndicator);
            viewHolder.llRemoveCheckMark = (LinearLayout) convertView.findViewById(R.id.llRemoveCheckMark);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (financeItem instanceof StockQuote) {

            viewHolder.tvName.setText(((StockQuote) financeItem).getName());
            viewHolder.tvSymbol.setText(((StockQuote) financeItem).getSymbol());
            viewHolder.tvPrice.setText(((StockQuote) financeItem).getLastTradePrice());
            viewHolder.tvPriceChange.setText(((StockQuote) financeItem).getFormatedPriceChange());
            viewHolder.tvPriceChange.setTextColor(((StockQuote) financeItem).getPriceColor(context));
         //   viewHolder.tvStockLetter.setText(((StockQuote) financeItem).getBigLetter());
            viewHolder.tvStockLetter.setTextColor(((StockQuote) financeItem).getPriceColor(context));
            viewHolder.viewPriceIndicator.setBackgroundColor(((StockQuote) financeItem).getPriceColor(context));

            if (financeItem.isRemoveMode) {
                viewHolder.tvStockLetter.setVisibility(View.GONE);
                viewHolder.llRemoveCheckMark.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvStockLetter.setVisibility(View.GONE);
                viewHolder.llRemoveCheckMark.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    public void clearRemoveModes() {
        for (FinanceItem item : financeItems) {
            item.isRemoveMode = false;
        }
    }


    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void removeItems(HashSet<Integer> financeItemsToRemove) {
        for (int position : financeItemsToRemove) {
            FinanceItem financeItem = getItem(position);
            PreferencesManager.getInstance().removeStockSymbolFromPrefs(((StockQuote) financeItem).getSymbol());
            remove(financeItem);
        }
    }

    @Override
    public void swapItems(int i1, int i2) {
        Collections.swap(financeItems, i1, i2);
        notifyDataSetChanged();
    }

    public void saveOrder() {
        ArrayList<String> stocksList = new ArrayList<String>();
        for (FinanceItem item : financeItems) {
            stocksList.add(((StockQuote) item).getSymbol());
        }
        PreferencesManager.getInstance().saveStockList(stocksList);
    }

    public void orderByAlphabet() {
        Collections.sort(financeItems);
    }
}
