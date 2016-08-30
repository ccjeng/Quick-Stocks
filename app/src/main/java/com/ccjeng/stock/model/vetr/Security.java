package com.ccjeng.stock.model.vetr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Security {

    @SerializedName("securityMasterId")
    @Expose
    private Long securityMasterId;
    @SerializedName("ticker")
    @Expose
    private String ticker;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("exchange")
    @Expose
    private String exchange;
    @SerializedName("securityWallId")
    @Expose
    private Long securityWallId;
    @SerializedName("fullTicker")
    @Expose
    private String fullTicker;
    @SerializedName("resultScore")
    @Expose
    private Long resultScore;
    @SerializedName("secType")
    @Expose
    private Object secType;
    @SerializedName("remark")
    @Expose
    private Object remark;
    @SerializedName("sector")
    @Expose
    private String sector;
    @SerializedName("industry")
    @Expose
    private String industry;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("active")
    @Expose
    private Boolean active;

    /**
     * 
     * @return
     *     The securityMasterId
     */
    public Long getSecurityMasterId() {
        return securityMasterId;
    }

    /**
     * 
     * @param securityMasterId
     *     The securityMasterId
     */
    public void setSecurityMasterId(Long securityMasterId) {
        this.securityMasterId = securityMasterId;
    }

    /**
     * 
     * @return
     *     The ticker
     */
    public String getTicker() {
        return ticker;
    }

    /**
     * 
     * @param ticker
     *     The ticker
     */
    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    /**
     * 
     * @return
     *     The company
     */
    public String getCompany() {
        return company;
    }

    /**
     * 
     * @param company
     *     The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 
     * @return
     *     The exchange
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * 
     * @param exchange
     *     The exchange
     */
    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    /**
     * 
     * @return
     *     The securityWallId
     */
    public Long getSecurityWallId() {
        return securityWallId;
    }

    /**
     * 
     * @param securityWallId
     *     The securityWallId
     */
    public void setSecurityWallId(Long securityWallId) {
        this.securityWallId = securityWallId;
    }

    /**
     * 
     * @return
     *     The fullTicker
     */
    public String getFullTicker() {
        return fullTicker;
    }

    /**
     * 
     * @param fullTicker
     *     The fullTicker
     */
    public void setFullTicker(String fullTicker) {
        this.fullTicker = fullTicker;
    }

    /**
     * 
     * @return
     *     The resultScore
     */
    public Long getResultScore() {
        return resultScore;
    }

    /**
     * 
     * @param resultScore
     *     The resultScore
     */
    public void setResultScore(Long resultScore) {
        this.resultScore = resultScore;
    }

    /**
     * 
     * @return
     *     The secType
     */
    public Object getSecType() {
        return secType;
    }

    /**
     * 
     * @param secType
     *     The secType
     */
    public void setSecType(Object secType) {
        this.secType = secType;
    }

    /**
     * 
     * @return
     *     The remark
     */
    public Object getRemark() {
        return remark;
    }

    /**
     * 
     * @param remark
     *     The remark
     */
    public void setRemark(Object remark) {
        this.remark = remark;
    }

    /**
     * 
     * @return
     *     The sector
     */
    public String getSector() {
        return sector;
    }

    /**
     * 
     * @param sector
     *     The sector
     */
    public void setSector(String sector) {
        this.sector = sector;
    }

    /**
     * 
     * @return
     *     The industry
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 
     * @param industry
     *     The industry
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * 
     * @return
     *     The website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * 
     * @param website
     *     The website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * 
     * @return
     *     The active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * 
     * @param active
     *     The active
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

}
