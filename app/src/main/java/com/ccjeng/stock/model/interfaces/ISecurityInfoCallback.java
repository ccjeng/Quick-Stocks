package com.ccjeng.stock.model.interfaces;

import com.ccjeng.stock.model.vetr.SecurityInfo;

/**
 * Created by andycheng on 2016/8/30.
 */
public interface ISecurityInfoCallback {

    void onQueryReceived(SecurityInfo securityInfo);

}
