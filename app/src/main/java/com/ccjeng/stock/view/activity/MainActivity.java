package com.ccjeng.stock.view.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ccjeng.stock.R;
import com.ccjeng.stock.Stock;
import com.ccjeng.stock.view.adapter.FinanceItemsAdapter;
import com.ccjeng.stock.controller.SearchAutoCompleterAdapter;
import com.ccjeng.stock.controller.StockQuoteAPI;
import com.ccjeng.stock.model.FinanceItem;
import com.ccjeng.stock.model.google.StockQuote;
import com.ccjeng.stock.model.interfaces.IStockQuoteCallback;
import com.ccjeng.stock.utils.GlobalUtils;
import com.ccjeng.stock.utils.PreferencesManager;
import com.ccjeng.stock.view.base.BaseActivity;
import com.google.android.gms.analytics.Tracker;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements SearchView.OnQueryTextListener
        , MenuItemCompat.OnActionExpandListener
        , AdapterView.OnItemClickListener
        , AdapterView.OnItemLongClickListener
        , SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "MainActivity";

    public static enum Mode {
        NORMAL, REMOVE, SEARCH, SORT;
    }

    public static Mode mode;

    private static final int REMOVE_MODE_ANIMATION_DURATION = 250;
    private static final String TOOLBAR_REMOVE_MODE_SPACES = "     ";

    public static final String INTENT_EXTRA_STOCK = "intentStock";

    private Tracker mTracker;

    private MenuItem editMenuItem;
    private MenuItem removeMenuItem;
    private MenuItem searchMenuItem;
    private MenuItem sortAbMenuItem;

    private SearchView mSearchView;
    private SearchAutoCompleterAdapter searchAutoCompleterAdapter;
    public FinanceItemsAdapter financeItemsAdapter;
    private HashSet<Integer> financeItemsToRemove;

    @Bind(R.id.main) LinearLayout mainLayout;
    @Bind(R.id.lvFinanceItemsList) DynamicListView lvMainListview;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.progress_wheel) ProgressWheel progressWheel;
    @Bind(R.id.swipe_container) SwipeRefreshLayout mSwipeLayout;
    @Bind(R.id.navigation) NavigationView navigation;
    @Bind(R.id.drawerlayout) DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Stock application = (Stock) getApplication();
        mTracker = application.getDefaultTracker();

        setSupportActionBar(toolbar);

        navDrawer();

        mode = Mode.NORMAL;
        financeItemsToRemove = new HashSet<Integer>();
        searchAutoCompleterAdapter = new SearchAutoCompleterAdapter(MainActivity.this);

        lvMainListview.enableDragAndDrop();
        lvMainListview.setOnItemClickListener(this);
        lvMainListview.setOnItemLongClickListener(this);

        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeResources(android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        if (GlobalUtils.isNetworkConnected(this)){
            populateMainListview();
        } else {
            GlobalUtils.showErrorSnackBar(mainLayout, R.string.no_internet_connection);
        }

    }

    private void populateMainListview() {

        progressWheel.setVisibility(View.VISIBLE);

        ArrayList<FinanceItem> financeItems = new ArrayList<FinanceItem>();
        if (financeItemsAdapter == null) {
            financeItemsAdapter = new FinanceItemsAdapter(MainActivity.this, financeItems);
        }

        lvMainListview.setAdapter(financeItemsAdapter);

        ArrayList<String> stocksList = PreferencesManager.getInstance().getStockList();

        IStockQuoteCallback gotQuotesCallback = new IStockQuoteCallback() {
            @Override
            public void onQueryReceived(ArrayList<StockQuote> stockItems) {
                financeItemsAdapter.clear();
                financeItemsAdapter.addAll(stockItems);
                financeItemsAdapter.notifyDataSetChanged();
                progressWheel.setVisibility(View.GONE);
            }
        };
        StockQuoteAPI stockQuoteAPI = new StockQuoteAPI(stocksList.toArray(new String[stocksList.size()]));
        stockQuoteAPI.getStockQuote(gotQuotesCallback);

    }


    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                populateMainListview();
                mSwipeLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        removeMenuItem = menu.findItem(R.id.action_remove);
        editMenuItem = menu.findItem(R.id.action_edit);
        sortAbMenuItem = menu.findItem(R.id.action_sort);

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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                switch (mode) {
                    case NORMAL:
                        finish();
                        break;
                    case SEARCH:
                        searchMenuItem.collapseActionView();
                        break;
                    case SORT:
                        financeItemsAdapter.saveOrder();
                        startMode(Mode.NORMAL);
                        break;
                }
                break;

            case R.id.action_remove:
                financeItemsAdapter.removeItems(financeItemsToRemove);
                //refresh data
                populateMainListview();
                financeItemsToRemove.clear();
                startMode(Mode.NORMAL);
                break;
            case R.id.action_sort:
                financeItemsAdapter.orderByAlphabet();
                financeItemsAdapter.saveOrder();
                financeItemsAdapter.notifyDataSetChanged();
                break;
            case R.id.action_edit:
                if (GlobalUtils.isNetworkConnected(this)) {
                    startMode(Mode.SORT);
                }
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void startMode(Mode modeToStart) {

        switch (mode) {
            case SORT:
                //Save order before exit sort mode
                financeItemsAdapter.saveOrder();
                break;
            case NORMAL:
                break;
            case REMOVE:
                //Clear listview cache after exiting form remove mode.
                lvMainListview.invalidate();
                financeItemsAdapter.clearRemoveModes();
                financeItemsAdapter.notifyDataSetChanged();
                break;
        }

        switch (modeToStart) {
            case NORMAL:
                removeMenuItem.setVisible(false);
                searchMenuItem.setVisible(true);
                editMenuItem.setVisible(true);
                sortAbMenuItem.setVisible(false);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                toolbar.setLogo(null);
                toolbar.setTitle(getString(R.string.app_name));
                toolbar.setBackgroundResource(R.color.colorPrimary);

                actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
                drawerLayout.setEnabled(true);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                actionBarDrawerToggle.setHomeAsUpIndicator(null);
                break;
            case REMOVE:
                removeMenuItem.setVisible(true);
                searchMenuItem.setVisible(false);
                editMenuItem.setVisible(false);
                sortAbMenuItem.setVisible(false);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                toolbar.setLogo(R.mipmap.icon_toolbar_checked);
                toolbar.setTitle(TOOLBAR_REMOVE_MODE_SPACES + "0 " + getString(R.string.from) + " " + String.valueOf(financeItemsAdapter.getCount()));
                toolbar.setBackgroundResource(R.color.price_red);

                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                drawerLayout.setEnabled(false);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                financeItemsToRemove.clear();
                financeItemsAdapter.clearRemoveModes();
                break;
            case SEARCH:
                searchMenuItem.setVisible(false);
                removeMenuItem.setVisible(false);
                editMenuItem.setVisible(false);
                sortAbMenuItem.setVisible(false);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                break;
            case SORT:
                searchMenuItem.setVisible(false);
                removeMenuItem.setVisible(false);
                editMenuItem.setVisible(false);
                sortAbMenuItem.setVisible(true);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                toolbar.setTitle(getString(R.string.drag_drop));
                toolbar.setBackgroundResource(R.color.price_green);
                toolbar.setLogo(null);

                actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
                drawerLayout.setEnabled(false);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                actionBarDrawerToggle.setHomeAsUpIndicator(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_arrow_back)
                .color(Color.WHITE)
                .sizeDp(24));


                actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        startMode(Mode.NORMAL);
                    }
                });
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(getResources().getColor(R.color.status_bar_green));
                }
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
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        return true;
    }

    @Override
    public void onBackPressed() {

        switch (mode) {
            case NORMAL:
                finish();
                break;
            case SEARCH:
                searchMenuItem.collapseActionView();
                break;
            case SORT:
                financeItemsAdapter.saveOrder();
                startMode(Mode.NORMAL);
                break;
            case REMOVE:
                startMode(Mode.NORMAL);
                break;

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
                imgAddFavorite.setImageResource(R.mipmap.img_checkmark);
            } else {
                PreferencesManager.getInstance().addStockSymbolToPrefs(clickedStockSymbol);
                imgAddFavorite.setImageResource(R.mipmap.img_checkmark_orange);
            }
        } else {

            if (mode == Mode.REMOVE) {
                markAsRemove(view, position);
            } else {
                //Toast.makeText(this, financeItemsAdapter.getItem(position).toString(), Toast.LENGTH_LONG).show();

                //Intent startIntent = new Intent(this, DetailActivity.class);
                Intent startIntent = new Intent(this, StockMainActivity.class);

                startIntent.putExtra(INTENT_EXTRA_STOCK, financeItemsAdapter.getItem(position));
                startActivity(startIntent);

            }
        }

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        switch (mode) {
            case NORMAL:
                startMode(Mode.REMOVE);
                markAsRemove(view, position);
                break;
            case REMOVE:
                markAsRemove(view, position);
                break;
            case SORT:
                lvMainListview.startDragging(position);
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

        toolbar.setTitle(TOOLBAR_REMOVE_MODE_SPACES + String.valueOf(financeItemsToRemove.size()) + " " + getString(R.string.from) + " " + String.valueOf(financeItemsAdapter.getCount()));

    }


    private void navDrawer() {
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked())
                    menuItem.setChecked(false);
                else
                    menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
               /*     case R.id.navSetting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;*/
                    case R.id.navAbout:
                       new LibsBuilder()
                                //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                                .withAboutIconShown(true)
                                .withAboutVersionShown(true)
                                .withAboutAppName(getString(R.string.app_name))
                                .withActivityTitle(getString(R.string.action_about))
                                .start(MainActivity.this);
                        break;

                }
                return false;
            }
        });

        //change navigation drawer item icons
        /*navigation.getMenu().findItem(R.id.navSetting).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_settings)
                .color(Color.GRAY)
                .sizeDp(24));*/

        navigation.getMenu().findItem(R.id.navAbout).setIcon(new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_info)
                .color(Color.GRAY)
                .sizeDp(24));


        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar
                ,R.string.app_name, R.string.app_name){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }
}
