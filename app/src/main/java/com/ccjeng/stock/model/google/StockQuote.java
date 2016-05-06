
package com.ccjeng.stock.model.google;

import android.content.Context;

import com.ccjeng.stock.R;
import com.ccjeng.stock.model.FinanceItem;

import java.util.HashMap;
import java.util.Map;

public class StockQuote  extends FinanceItem  {

    private String id; //ID
    private String t;  //StockSymbol
    private String e;  //Market
    private String l; //LastTradePrice - close price (previous trading day)
    private String lFix;
    private String lCur; //LastTradeWithCurrency
    private String s;    //LastTradeSize
    private String ltt; //LastTradeTime "4:00PM EDT"
    private String lt;    //LastTradeDateTimeLong "Mar 16, 4:00PM EDT"
    private String ltDts; //LastTradeDateTime ""2016-03-16T16:00:02Z"
    private String c;     //Change
    private String cFix;
    private String cp;    //Change Percent
    private String cpFix;
    private String ccol;
    private String pcls_fix; //PreviousClosePrice
    private String el;      //ExtHrsLastTradePrice - stock price in pre-market (after-hours)
    private String elFix;
    private String elCur;   //ExtHrsLastTradeWithCurrency
    private String elt;     //After Hour Time
    private String ec;      //ExtHrsChange - After Hour Change
    private String ecFix;
    private String ecp;     //ExtHrsChangePercent - After Hour Change Percent
    private String ecpFix;
    private String eccol;
    private String div;     //Div
    private String yld;     //Yield
    private String eo;
    private String delay;
    private String op;      //Open Price
    private String hi;      //Day High
    private String lo;      //Day Low
    private String vo;      //Volume
    private String avvo;    //Avg Volume
    private String hi52;    //High 52
    private String lo52;    //Low 52
    private String mc;      //Market Capital
    private String pe;      //PE Ratio
    private String fwpe;
    private String beta;    //Beta
    private String eps;     //EPS
    private String shares;  //Shares
    private String instOwn;
    private String name;    //Name
    private String type;    //Type
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The t
     */
    public String getT() {
        return t;
    }
    public String getSymbol() {
        return t;
    }

    /**
     * 
     * @param t
     *     The t
     */
    public void setT(String t) {
        this.t = t;
    }

    /**
     * 
     * @return
     *     The e
     */
    public String getE() {
        return e;
    }

    public String getMarket() {
        return e;
    }

    /**
     * 
     * @param e
     *     The e
     */
    public void setE(String e) {
        this.e = e;
    }

    /**
     * 
     * @return
     *     The l
     */
    public String getL() {
        return l;
    }
    public String getLastTradePrice() {
        return l;
    }

    /**
     * 
     * @param l
     *     The l
     */
    public void setL(String l) {
        this.l = l;
    }

    /**
     * 
     * @return
     *     The lFix
     */
    public String getLFix() {
        return lFix;
    }

    /**
     * 
     * @param lFix
     *     The l_fix
     */
    public void setLFix(String lFix) {
        this.lFix = lFix;
    }

    /**
     * 
     * @return
     *     The lCur
     */
    public String getLCur() {
        return lCur;
    }
    public String getLastTradeWithCurrency() {
        return lCur;
    }


    /**
     * 
     * @param lCur
     *     The l_cur
     */
    public void setLCur(String lCur) {
        this.lCur = lCur;
    }

    /**
     * 
     * @return
     *     The s
     */
    public String getS() {
        return s;
    }
    public String getLastTradeSize() {
        return s;
    }

    /**
     * 
     * @param s
     *     The s
     */
    public void setS(String s) {
        this.s = s;
    }

    /**
     * 
     * @return
     *     The ltt
     */
    public String getLtt() {
        return ltt;
    }
    public String getLastTradeTime() {
        return ltt;
    }

    /**
     * 
     * @param ltt
     *     The ltt
     */
    public void setLtt(String ltt) {
        this.ltt = ltt;
    }

    /**
     * 
     * @return
     *     The lt
     */
    public String getLt() {
        return lt;
    }
    public String getLastTradeDateTimeLong() {
        return lt;
    }

    /**
     * 
     * @param lt
     *     The lt
     */
    public void setLt(String lt) {
        this.lt = lt;
    }

    /**
     * 
     * @return
     *     The ltDts
     */
    public String getLtDts() {
        return ltDts;
    }
    public String getLastTradeDateTime() {
        return ltDts;
    }

    /**
     * 
     * @param ltDts
     *     The lt_dts
     */
    public void setLtDts(String ltDts) {
        this.ltDts = ltDts;
    }

    /**
     * 
     * @return
     *     The c
     */
    public String getC() {
        return c;
    }
    public String getChange() {
        return c;
    }


    /**
     * 
     * @param c
     *     The c
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     * 
     * @return
     *     The cFix
     */
    public String getCFix() {
        return cFix;
    }

    /**
     * 
     * @param cFix
     *     The c_fix
     */
    public void setCFix(String cFix) {
        this.cFix = cFix;
    }

    /**
     * 
     * @return
     *     The cp
     */
    public String getCp() {
        return cp;
    }
    public String getChangePercent() {
        return cp;
    }


    /**
     * 
     * @param cp
     *     The cp
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * 
     * @return
     *     The cpFix
     */
    public String getCpFix() {
        return cpFix;
    }

    /**
     * 
     * @param cpFix
     *     The cp_fix
     */
    public void setCpFix(String cpFix) {
        this.cpFix = cpFix;
    }

    /**
     * 
     * @return
     *     The ccol
     */
    public String getCcol() {
        return ccol;
    }

    /**
     * 
     * @param ccol
     *     The ccol
     */
    public void setCcol(String ccol) {
        this.ccol = ccol;
    }

    /**
     * 
     * @return
     *     The pclsFix
     */
    public String getPclsFix() {
        return pcls_fix;
    }
    public String getPreviousClosePrice() {
        return pcls_fix;
    }


    /**
     * 
     * @param pclsFix
     *     The pcls_fix
     */
    public void setPclsFix(String pclsFix) {
        this.pcls_fix = pclsFix;
    }

    /**
     * 
     * @return
     *     The el
     */
    public String getEl() {
        return el;
    }
    public String getAfterHourLastTradePrice() {
        if (el == null)
            return "";
        else
            return el;
    }


    /**
     * 
     * @param el
     *     The el
     */
    public void setEl(String el) {
        this.el = el;
    }

    /**
     * 
     * @return
     *     The elFix
     */
    public String getElFix() {
        return elFix;
    }

    /**
     * 
     * @param elFix
     *     The el_fix
     */
    public void setElFix(String elFix) {
        this.elFix = elFix;
    }

    /**
     * 
     * @return
     *     The elCur
     */
    public String getElCur() {
        return elCur;
    }
    public String getAfterHourLastTradePriceWithCurrency() {
        return elCur;
    }

    /**
     * 
     * @param elCur
     *     The el_cur
     */
    public void setElCur(String elCur) {
        this.elCur = elCur;
    }

    /**
     * 
     * @return
     *     The elt
     */
    public String getElt() {
        return elt;
    }
    public String getAfterHourTime() {
        if (elt == null)
            return "";
        else
            return elt;
    }

    /**
     * 
     * @param elt
     *     The elt
     */
    public void setElt(String elt) {
        this.elt = elt;
    }

    /**
     * 
     * @return
     *     The ec
     */
    public String getEc() {
        return ec;
    }
    public String getAfterHourChange() {
        if (ec == null)
            return "";
        else
            return ec;
    }

    /**
     * 
     * @param ec
     *     The ec
     */
    public void setEc(String ec) {
        this.ec = ec;
    }

    /**
     * 
     * @return
     *     The ecFix
     */
    public String getEcFix() {
        return ecFix;
    }

    /**
     * 
     * @param ecFix
     *     The ec_fix
     */
    public void setEcFix(String ecFix) {
        this.ecFix = ecFix;
    }

    /**
     * 
     * @return
     *     The ecp
     */
    public String getEcp() {
        return ecp;
    }
    public String getAfterHourChangePercent() {
        if (ecp == null)
            return "";
        else
            return ecp;
    }

    /**
     * 
     * @param ecp
     *     The ecp
     */
    public void setEcp(String ecp) {
        this.ecp = ecp;
    }

    /**
     * 
     * @return
     *     The ecpFix
     */
    public String getEcpFix() {
        return ecpFix;
    }

    /**
     * 
     * @param ecpFix
     *     The ecp_fix
     */
    public void setEcpFix(String ecpFix) {
        this.ecpFix = ecpFix;
    }

    /**
     * 
     * @return
     *     The eccol
     */
    public String getEccol() {
        return eccol;
    }

    /**
     * 
     * @param eccol
     *     The eccol
     */
    public void setEccol(String eccol) {
        this.eccol = eccol;
    }

    /**
     * 
     * @return
     *     The div
     */
    public String getDiv() {
        return div;
    }

    /**
     * 
     * @param div
     *     The div
     */
    public void setDiv(String div) {
        this.div = div;
    }

    /**
     * 
     * @return
     *     The yld
     */
    public String getYld() {
        return yld;
    }
    public String getYield() {
        return yld;
    }


    /**
     * 
     * @param yld
     *     The yld
     */
    public void setYld(String yld) {
        this.yld = yld;
    }

    /**
     * 
     * @return
     *     The eo
     */
    public String getEo() {
        return eo;
    }

    /**
     * 
     * @param eo
     *     The eo
     */
    public void setEo(String eo) {
        this.eo = eo;
    }

    /**
     * 
     * @return
     *     The delay
     */
    public String getDelay() {
        return delay;
    }

    /**
     * 
     * @param delay
     *     The delay
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * 
     * @return
     *     The op
     */
    public String getOp() {
        return op;
    }
    public String getOpenPrice() {
        return op;
    }


    /**
     * 
     * @param op
     *     The op
     */
    public void setOp(String op) {
        this.op = op;
    }

    /**
     * 
     * @return
     *     The hi
     */
    public String getHi() {
        return hi;
    }
    public String getDaysHigh() {
        return hi;
    }

    /**
     * 
     * @param hi
     *     The hi
     */
    public void setHi(String hi) {
        this.hi = hi;
    }

    /**
     * 
     * @return
     *     The lo
     */
    public String getLo() {
        return lo;
    }
    public String getDaysLow() {
        return lo;
    }

    /**
     * 
     * @param lo
     *     The lo
     */
    public void setLo(String lo) {
        this.lo = lo;
    }

    /**
     * 
     * @return
     *     The vo
     */
    public String getVo() {
        return vo;
    }
    public String getVolume() {
        return vo;
    }

    /**
     * 
     * @param vo
     *     The vo
     */
    public void setVo(String vo) {
        this.vo = vo;
    }

    /**
     * 
     * @return
     *     The avvo
     */
    public String getAvvo() {
        return avvo;
    }

    /**
     * 
     * @param avvo
     *     The avvo
     */
    public void setAvvo(String avvo) {
        this.avvo = avvo;
    }

    /**
     * 
     * @return
     *     The hi52
     */
    public String getHi52() {
        return hi52;
    }
    public String getYearHigh() {
        return hi52;
    }


    /**
     * 
     * @param hi52
     *     The hi52
     */
    public void setHi52(String hi52) {
        this.hi52 = hi52;
    }

    /**
     * 
     * @return
     *     The lo52
     */
    public String getLo52() {
        return lo52;
    }
    public String getYearLow() {
        return lo52;
    }

    /**
     * 
     * @param lo52
     *     The lo52
     */
    public void setLo52(String lo52) {
        this.lo52 = lo52;
    }

    /**
     * 
     * @return
     *     The mc
     */
    public String getMc() {
        return mc;
    }
    public String getMarketCapitalization() {
        return mc;
    }

    /**
     * 
     * @param mc
     *     The mc
     */
    public void setMc(String mc) {
        this.mc = mc;
    }

    /**
     * 
     * @return
     *     The pe
     */
    public String getPe() {
        return pe;
    }
    public String getPERatio() {
        return pe;
    }

    /**
     * 
     * @param pe
     *     The pe
     */
    public void setPe(String pe) {
        this.pe = pe;
    }

    /**
     * 
     * @return
     *     The fwpe
     */
    public String getFwpe() {
        return fwpe;
    }

    /**
     * 
     * @param fwpe
     *     The fwpe
     */
    public void setFwpe(String fwpe) {
        this.fwpe = fwpe;
    }

    /**
     * 
     * @return
     *     The beta
     */
    public String getBeta() {
        return beta;
    }

    /**
     * 
     * @param beta
     *     The beta
     */
    public void setBeta(String beta) {
        this.beta = beta;
    }

    /**
     * 
     * @return
     *     The eps
     */
    public String getEps() {
        return eps;
    }
    public String getEarningsShare() {
        return eps;
    }


    /**
     * 
     * @param eps
     *     The eps
     */
    public void setEps(String eps) {
        this.eps = eps;
    }

    /**
     * 
     * @return
     *     The shares
     */
    public String getShares() {
        return shares;
    }

    /**
     * 
     * @param shares
     *     The shares
     */
    public void setShares(String shares) {
        this.shares = shares;
    }

    /**
     * 
     * @return
     *     The instOwn
     */
    public String getInstOwn() {
        return instOwn;
    }

    /**
     * 
     * @param instOwn
     *     The inst_own
     */
    public void setInstOwn(String instOwn) {
        this.instOwn = instOwn;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        if (name.length() > 25)
            return name.trim().substring(0, 25) + "..";
        else
            return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public String getFormatedPriceChange() {
        return this.getChange() + " (" + this.getChangePercent() + "%)";

    }

    public int getPriceColor(Context context) {
        return this.getChange().contains("-") ? context.getResources().getColor(R.color.price_red) : context.getResources().getColor(R.color.price_green);

    }


    public String getFormatedPreMarketPriceChange() {
        return this.getAfterHourChange() + " (" + this.getAfterHourChangePercent() + "%)";

    }

    public int getPricePreMarketColor(Context context) {
        if (this.getAfterHourChange() !=null) {
            return this.getAfterHourChange().contains("-") ? context.getResources().getColor(R.color.price_red) : context.getResources().getColor(R.color.price_green);
        }
        else {
            return context.getResources().getColor(R.color.price_green);
        }

    }

    @Override
    public int compareTo(FinanceItem item) {
        return this.t.compareTo(((StockQuote) item).t);
    }
}
