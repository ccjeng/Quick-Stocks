
package com.ccjeng.stock.model.yahoo.historicaldata;

public class Quote {

    private String Symbol;
    private String Date;
    private String Open;
    private String High;
    private String Low;
    private String Close;
    private String Volume;
    private String AdjClose;

    /**
     * 
     * @return
     *     The Symbol
     */
    public String getSymbol() {
        return Symbol;
    }

    /**
     * 
     * @param Symbol
     *     The Symbol
     */
    public void setSymbol(String Symbol) {
        this.Symbol = Symbol;
    }

    /**
     * 
     * @return
     *     The Date
     */
    public String getDate() {
        return Date;
    }

    /**
     * 
     * @param Date
     *     The Date
     */
    public void setDate(String Date) {
        this.Date = Date;
    }

    /**
     * 
     * @return
     *     The Open
     */
    public String getOpen() {
        return Open;
    }

    /**
     * 
     * @param Open
     *     The Open
     */
    public void setOpen(String Open) {
        this.Open = Open;
    }

    /**
     * 
     * @return
     *     The High
     */
    public String getHigh() {
        return High;
    }

    /**
     * 
     * @param High
     *     The High
     */
    public void setHigh(String High) {
        this.High = High;
    }

    /**
     * 
     * @return
     *     The Low
     */
    public String getLow() {
        return Low;
    }

    /**
     * 
     * @param Low
     *     The Low
     */
    public void setLow(String Low) {
        this.Low = Low;
    }

    /**
     * 
     * @return
     *     The Close
     */
    public String getClose() {
        return Close;
    }

    /**
     * 
     * @param Close
     *     The Close
     */
    public void setClose(String Close) {
        this.Close = Close;
    }

    /**
     * 
     * @return
     *     The Volume
     */
    public String getVolume() {
        return Volume;
    }

    /**
     * 
     * @param Volume
     *     The Volume
     */
    public void setVolume(String Volume) {
        this.Volume = Volume;
    }

    /**
     * 
     * @return
     *     The AdjClose
     */
    public String getAdjClose() {
        return AdjClose;
    }

    /**
     * 
     * @param AdjClose
     *     The Adj_Close
     */
    public void setAdjClose(String AdjClose) {
        this.AdjClose = AdjClose;
    }

}
