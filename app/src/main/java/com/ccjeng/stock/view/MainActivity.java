package com.ccjeng.stock.view;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ccjeng.stock.R;
import com.ccjeng.stock.controller.FinanceItemsAdapter;
import com.ccjeng.stock.controller.SearchAutoCompleterAdapter;
import com.ccjeng.stock.controller.StockQuoteAPI;
import com.ccjeng.stock.model.FinanceItem;
import com.ccjeng.stock.model.interfaces.IStockQuoteCallback;
import com.ccjeng.stock.model.quotes.Quote;
import com.ccjeng.stock.utils.PreferencesManager;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener
        , MenuItemCompat.OnActionExpandListener
        , AdapterView.OnItemClickListener
        , AdapterView.OnItemLongClickListener {

    private static final String TAG = "MainActivity";

    public static enum Mode {
        NORMAL, REMOVE, SEARCH, SORT;
    }
    public static Mode mode;

    private static final int REMOVE_MODE_ANIMATION_DURATION = 250;
    public static final String INTENT_EXTRA_STOCK = "intentStock";

    private MenuItem editMenuItem;
    private MenuItem removeMenuItem;
    private MenuItem searchMenuItem;
    private MenuItem sortAbMenuItem;

    private SearchView mSearchView;
    private SearchAutoCompleterAdapter searchAutoCompleterAdapter;
    public FinanceItemsAdapter financeItemsAdapter;
    private HashSet<Integer> financeItemsToRemove;

    private IStockQuoteCallback gotQuotesCallback;


    @Bind(R.id.lvFinanceItemsList) public DynamicListView lvMainListview;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        mode = Mode.NORMAL;
        financeItemsToRemove = new HashSet<Integer>();
        searchAutoCompleterAdapter = new SearchAutoCompleterAdapter(MainActivity.this);

        lvMainListview.enableDragAndDrop();
        lvMainListview.setOnItemClickListener(this);
        lvMainListview.setOnItemLongClickListener(this);

        populateMainListview();


    }

    private void populateMainListview() {

        ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
        if (financeItemsAdapter == null) {
            financeItemsAdapter = new FinanceItemsAdapter(MainActivity.this, financeItems);
        }

        lvMainListview.setAdapter(financeItemsAdapter);

        ArrayList<String> stocksList = PreferencesManager.getInstance().getStockList();

        gotQuotesCallback = new IStockQuoteCallback() {
            @Override
            public void onQueryReceived(ArrayList<Quote> stockItems) {
                financeItemsAdapter.clear();
                financeItemsAdapter.addAll(stockItems);
                financeItemsAdapter.notifyDataSetChanged();
            }
        };
        StockQuoteAPI stockQuoteAPI = new StockQuoteAPI(stocksList.toArray(new String[stocksList.size()]));
        stockQuoteAPI.getStockQuote(gotQuotesCallback);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        removeMenuItem = menu.findItem(R.id.action_remove);
        editMenuItem = menu.findItem(R.id.action_edit);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) searchMenuItem.getActionView();

        // Assumes current activity is the searchable activity
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        mSearchView.setOnQueryTextListener(this);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, this);

        // 這邊讓icon可以還原到搜尋的icon
        mSearchView.setIconifiedByDefault(true);

        startMode(Mode.NORMAL);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.equals(removeMenuItem)) {
            financeItemsAdapter.removeItems(financeItemsToRemove);
            financeItemsToRemove.clear();
            startMode(Mode.NORMAL);
        }
        return super.onOptionsItemSelected(item);
    }


    private void startMode(Mode modeToStart) {

        switch(mode){
            case NORMAL:
                break;
            case REMOVE:
                //Clear listview cache after exiting form remove mode.
                lvMainListview.invalidate();
                financeItemsAdapter.clearRemoveModes();
                financeItemsAdapter.notifyDataSetChanged();
                break;
        }

        switch(modeToStart) {
            case NORMAL:
                removeMenuItem.setVisible(false);
                searchMenuItem.setVisible(true);
                editMenuItem.setVisible(false);
                break;
            case REMOVE:
                removeMenuItem.setVisible(true);
                searchMenuItem.setVisible(false);
                editMenuItem.setVisible(false);

                financeItemsToRemove.clear();
                financeItemsAdapter.clearRemoveModes();
                break;
            case SEARCH:
                searchMenuItem.setVisible(false);
                removeMenuItem.setVisible(false);
                editMenuItem.setVisible(false);
                break;
            case SORT:
                break;
        }
        mode = modeToStart;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchAutoCompleterAdapter.autocomplete(newText);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        startMode(Mode.NORMAL);
        lvMainListview.setAdapter(financeItemsAdapter);
        populateMainListview();
        return true;
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        startMode(Mode.SEARCH);
        lvMainListview.setAdapter(searchAutoCompleterAdapter);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mode == Mode.SEARCH) {
            searchMenuItem.collapseActionView();
        } else if (mode != Mode.NORMAL) {
            startMode(Mode.NORMAL);
        } else {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

        if (((ListView) listView).getAdapter() instanceof SearchAutoCompleterAdapter) {
            //Add
            String clickedStockSymbol = searchAutoCompleterAdapter.getItem(position).symbol;
            ImageView imgAddFavorite = (ImageView) view.findViewById(R.id.imgAddFavorite);
            if (PreferencesManager.getInstance().stocksSetContains(clickedStockSymbol)) {
                PreferencesManager.getInstance().removeStockSymbolFromPrefs(clickedStockSymbol);
                imgAddFavorite.setImageResource(R.drawable.img_checkmark);
            } else {
                PreferencesManager.getInstance().addStockSymbolToPrefs(clickedStockSymbol);
                imgAddFavorite.setImageResource(R.drawable.img_checkmark_orange);
            }
        } else {

            if (mode == Mode.REMOVE) {
                markAsRemove(view, position);
            } else {
                //Toast.makeText(this, financeItemsAdapter.getItem(position).toString(), Toast.LENGTH_LONG).show();

                Intent startIntent = new Intent(this, DetailActivity.class);
                startIntent.putExtra(INTENT_EXTRA_STOCK, financeItemsAdapter.getItem(position));
                startActivity(startIntent);

            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        switch(mode) {
            case NORMAL:
                startMode(Mode.REMOVE);
                markAsRemove(view, position);
                break;
            case REMOVE:
                markAsRemove(view, position);
                break;

        }
        return true;
    }


    private void markAsRemove(View view, int position) {
        final int itemPosition = position;
        final TextView tvStockLetter = (TextView) view.findViewById(R.id.tvStockLetter);
        final LinearLayout llRemoveCheckMark = (LinearLayout) view.findViewById(R.id.llRemoveCheckMark);
        if (llRemoveCheckMark.getVisibility() == View.GONE) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tvStockLetter.setVisibility(View.GONE);
                            llRemoveCheckMark.setVisibility(View.VISIBLE);
                            (financeItemsAdapter.getItem(itemPosition)).isRemoveMode = true;
                        }
                    }, REMOVE_MODE_ANIMATION_DURATION);
                }
            });
            financeItemsToRemove.add(position);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            llRemoveCheckMark.setVisibility(View.GONE);
                            tvStockLetter.setVisibility(View.GONE);
                            (financeItemsAdapter.getItem(itemPosition)).isRemoveMode = false;
                        }
                    }, REMOVE_MODE_ANIMATION_DURATION);
                }
            });
            financeItemsToRemove.remove(position);
        }

        //mActionBarToolbar.setTitle(TOOLBAR_REMOVE_MODE_SPACES + String.valueOf(financeItemsToRemove.size()) + " " + getString(R.string.from) + " " + String.valueOf(financeItemsAdapter.getCount()));

    }
}
