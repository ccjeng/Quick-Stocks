package com.ccjeng.stock.controller;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.interfaces.CompanyLookupService;
import com.ccjeng.stock.model.CompanyLookup;
import com.ccjeng.stock.model.SearchAutocompleteItem;
import com.ccjeng.stock.utils.Constant;
import com.ccjeng.stock.utils.PreferencesManager;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by andycheng on 2016/3/4.
 */
public class SearchAutoCompleterAdapter extends BaseAdapter
        implements Swappable {

    private static final String TAG = "SearchAutoCompleter";
    private class ViewHolder {
        TextView tvStockName;
        TextView tvStockExchange;
        TextView tvStockSymbol;
        ImageView imgFavorite;
    }

    private ArrayList<SearchAutocompleteItem> autocompleteItems;
    private Context context;

    public SearchAutoCompleterAdapter(Context context) {
        super();
        this.autocompleteItems = new ArrayList<SearchAutocompleteItem>();
        this.context = context;
    }
    public void autocomplete(String input) {
        autocompleteItems.clear();

        try {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okhttpClient = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.ENDPOINT_MOD)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okhttpClient)
                    .build();

            CompanyLookupService lookupService = retrofit.create(CompanyLookupService.class);

            lookupService.getCompany(input)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<List<CompanyLookup>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.d("Error", e.getMessage());
                        }

                        @Override
                        public void onNext(List<CompanyLookup> companyLookups) {
                            for(CompanyLookup company : companyLookups) {
                                Log.d(TAG, company.getSymbol());

                                SearchAutocompleteItem item = new SearchAutocompleteItem(
                                        company.getName(),
                                        company.getSymbol(),
                                        company.getExchange()
                                );
                                item.order = SearchAutocompleteItem.MAX_ORDER;

                                if (!company.getExchange().contains("BATS")) {
                                    autocompleteItems.add(item);
                                }
                            }

                            if (autocompleteItems != null && autocompleteItems.size() > 0) {
                                notifyDataSetChanged();
                            } else {
                                notifyDataSetInvalidated();
                            }

                        }
                    });

        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchAutocompleteItem searchAutocompleteItem = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.lv_search_item, parent, false);
            viewHolder.tvStockSymbol = (TextView) convertView.findViewById(R.id.tvStockSymbol);
            viewHolder.tvStockName = (TextView) convertView.findViewById(R.id.tvStockName);
            viewHolder.tvStockExchange = (TextView) convertView.findViewById(R.id.tvStockExchange);

            viewHolder.imgFavorite = (ImageView) convertView.findViewById(R.id.imgAddFavorite);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvStockName.setText(searchAutocompleteItem.name);
        viewHolder.tvStockSymbol.setText(searchAutocompleteItem.symbol);
        viewHolder.tvStockExchange.setText(searchAutocompleteItem.exchange);

        if (PreferencesManager.getInstance().stocksSetContains(searchAutocompleteItem.symbol)) {
            viewHolder.imgFavorite.setImageResource(R.drawable.img_checkmark_orange);
        } else {
            viewHolder.imgFavorite.setImageResource(R.drawable.img_checkmark);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return autocompleteItems.size();
    }

    @Override
    public SearchAutocompleteItem getItem(int position) {
        return autocompleteItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).hashCode();
    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public void swapItems(int positionOne, int positionTwo) {

    }


}
