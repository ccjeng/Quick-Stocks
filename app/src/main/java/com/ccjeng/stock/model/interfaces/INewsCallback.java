package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.rss.RSSFeed;

/**
 * Created by andycheng on 2016/3/21.
 */
public interface INewsCallback {

    public void onRSSReceived(RSSFeed rssFeed);

}
