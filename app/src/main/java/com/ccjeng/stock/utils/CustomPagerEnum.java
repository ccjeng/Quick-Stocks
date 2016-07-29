package com.ccjeng.stock.utils;

import com.ccjeng.stock.R;

/**
 * Created by andycheng on 2016/7/29.
 */
public enum CustomPagerEnum {

    INFO(R.string.info, R.layout.fragment_stock_info),
    NEWS(R.string.news, R.layout.fragment_stock_news),
    OTHERS(R.string.others, R.layout.fragment_stock_news);

    private int mTitleResId;
    private int mLayoutResId;

    CustomPagerEnum(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}
