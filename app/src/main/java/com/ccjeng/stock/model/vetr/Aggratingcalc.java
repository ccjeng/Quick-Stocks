package com.ccjeng.stock.model.vetr;

import com.ccjeng.stock.utils.GlobalUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Aggratingcalc {

    @SerializedName("securityMasterId")
    @Expose
    private Long securityMasterId;
    @SerializedName("ratingComputeDate")
    @Expose
    private String ratingComputeDate;
    @SerializedName("aggRatingScore")
    @Expose
    private Float aggRatingScore;
    @SerializedName("totalRatings")
    @Expose
    private Long totalRatings;
    @SerializedName("bullishCount")
    @Expose
    private Long bullishCount;
    @SerializedName("bearishCount")
    @Expose
    private Long bearishCount;
    @SerializedName("totalBuys")
    @Expose
    private Long totalBuys;
    @SerializedName("totalSells")
    @Expose
    private Long totalSells;
    @SerializedName("totalHolds")
    @Expose
    private Long totalHolds;
    @SerializedName("avgTarget")
    @Expose
    private Double avgTarget;
    @SerializedName("avgTargetPct")
    @Expose
    private Double avgTargetPct;
    @SerializedName("avgTargetOrganic")
    @Expose
    private Double avgTargetOrganic;
    @SerializedName("avgTargetPctOrganic")
    @Expose
    private Double avgTargetPctOrganic;
    @SerializedName("avgTargetAutomated")
    @Expose
    private Double avgTargetAutomated;
    @SerializedName("avgTargetPctAutomated")
    @Expose
    private Double avgTargetPctAutomated;
    @SerializedName("avg3mTarget")
    @Expose
    private Double avg3mTarget;
    @SerializedName("avg3mTargetPct")
    @Expose
    private Double avg3mTargetPct;
    @SerializedName("avg6mTarget")
    @Expose
    private Double avg6mTarget;
    @SerializedName("avg6mTargetPct")
    @Expose
    private Double avg6mTargetPct;
    @SerializedName("avg12mTarget")
    @Expose
    private Double avg12mTarget;
    @SerializedName("avg12mTargetPct")
    @Expose
    private Double avg12mTargetPct;
    @SerializedName("currentPrice")
    @Expose
    private Double currentPrice;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("prev_score")
    @Expose
    private Double prevScore;
    @SerializedName("totalStrongSellRatings")
    @Expose
    private Long totalStrongSellRatings;
    @SerializedName("totalSellRatings")
    @Expose
    private Long totalSellRatings;
    @SerializedName("totalHoldRatings")
    @Expose
    private Long totalHoldRatings;
    @SerializedName("totalBuyRatings")
    @Expose
    private Long totalBuyRatings;
    @SerializedName("totalStrongBuyRatings")
    @Expose
    private Long totalStrongBuyRatings;
    @SerializedName("arnTargetPrice")
    @Expose
    private Double arnTargetPrice;
    @SerializedName("valid")
    @Expose
    private Boolean valid;

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
     *     The ratingComputeDate
     */
    public String getRatingComputeDate() {
        return ratingComputeDate;
    }

    /**
     * 
     * @param ratingComputeDate
     *     The ratingComputeDate
     */
    public void setRatingComputeDate(String ratingComputeDate) {
        this.ratingComputeDate = ratingComputeDate;
    }

    /**
     * 
     * @return
     *     The aggRatingScore
     */
    public Float getAggRatingScore() {
        return aggRatingScore;
    }


    /**
     * 
     * @param aggRatingScore
     *     The aggRatingScore
     */
    public void setAggRatingScore(Float aggRatingScore) {
        this.aggRatingScore = aggRatingScore;
    }

    /**
     * 
     * @return
     *     The totalRatings
     */
    public Long getTotalRatings() {
        return totalRatings;
    }

    /**
     * 
     * @param totalRatings
     *     The totalRatings
     */
    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }

    /**
     * 
     * @return
     *     The bullishCount
     */
    public Long getBullishCount() {
        return bullishCount;
    }

    /**
     * 
     * @param bullishCount
     *     The bullishCount
     */
    public void setBullishCount(Long bullishCount) {
        this.bullishCount = bullishCount;
    }

    /**
     * 
     * @return
     *     The bearishCount
     */
    public Long getBearishCount() {
        return bearishCount;
    }

    /**
     * 
     * @param bearishCount
     *     The bearishCount
     */
    public void setBearishCount(Long bearishCount) {
        this.bearishCount = bearishCount;
    }

    /**
     * 
     * @return
     *     The totalBuys
     */
    public Long getTotalBuys() {
        return totalBuys;
    }

    /**
     * 
     * @param totalBuys
     *     The totalBuys
     */
    public void setTotalBuys(Long totalBuys) {
        this.totalBuys = totalBuys;
    }

    /**
     * 
     * @return
     *     The totalSells
     */
    public Long getTotalSells() {
        return totalSells;
    }

    /**
     * 
     * @param totalSells
     *     The totalSells
     */
    public void setTotalSells(Long totalSells) {
        this.totalSells = totalSells;
    }

    /**
     * 
     * @return
     *     The totalHolds
     */
    public Long getTotalHolds() {
        return totalHolds;
    }

    /**
     * 
     * @param totalHolds
     *     The totalHolds
     */
    public void setTotalHolds(Long totalHolds) {
        this.totalHolds = totalHolds;
    }

    /**
     * 
     * @return
     *     The avgTarget
     */
    public Double getAvgTarget() {
        return avgTarget;
    }

    public String getAvgTargetString() {
        return GlobalUtils.NumberToString(avgTarget);
    }

    /**
     * 
     * @param avgTarget
     *     The avgTarget
     */
    public void setAvgTarget(Double avgTarget) {
        this.avgTarget = avgTarget;
    }

    /**
     * 
     * @return
     *     The avgTargetPct
     */
    public Double getAvgTargetPct() {
        return avgTargetPct;
    }

    public String getAvgTargetPctString() {
        return GlobalUtils.NumberToString(100*avgTargetPct) + " %";
    }

    /**
     * 
     * @param avgTargetPct
     *     The avgTargetPct
     */
    public void setAvgTargetPct(Double avgTargetPct) {
        this.avgTargetPct = avgTargetPct;
    }

    /**
     * 
     * @return
     *     The avgTargetOrganic
     */
    public Double getAvgTargetOrganic() {
        return avgTargetOrganic;
    }

    /**
     * 
     * @param avgTargetOrganic
     *     The avgTargetOrganic
     */
    public void setAvgTargetOrganic(Double avgTargetOrganic) {
        this.avgTargetOrganic = avgTargetOrganic;
    }

    /**
     * 
     * @return
     *     The avgTargetPctOrganic
     */
    public Double getAvgTargetPctOrganic() {
        return avgTargetPctOrganic;
    }

    /**
     * 
     * @param avgTargetPctOrganic
     *     The avgTargetPctOrganic
     */
    public void setAvgTargetPctOrganic(Double avgTargetPctOrganic) {
        this.avgTargetPctOrganic = avgTargetPctOrganic;
    }

    /**
     * 
     * @return
     *     The avgTargetAutomated
     */
    public Double getAvgTargetAutomated() {
        return avgTargetAutomated;
    }

    /**
     * 
     * @param avgTargetAutomated
     *     The avgTargetAutomated
     */
    public void setAvgTargetAutomated(Double avgTargetAutomated) {
        this.avgTargetAutomated = avgTargetAutomated;
    }

    /**
     * 
     * @return
     *     The avgTargetPctAutomated
     */
    public Double getAvgTargetPctAutomated() {
        return avgTargetPctAutomated;
    }

    /**
     * 
     * @param avgTargetPctAutomated
     *     The avgTargetPctAutomated
     */
    public void setAvgTargetPctAutomated(Double avgTargetPctAutomated) {
        this.avgTargetPctAutomated = avgTargetPctAutomated;
    }

    /**
     * 
     * @return
     *     The avg3mTarget
     */
    public Double getAvg3mTarget() {
        return avg3mTarget;
    }

    /**
     * 
     * @param avg3mTarget
     *     The avg3mTarget
     */
    public void setAvg3mTarget(Double avg3mTarget) {
        this.avg3mTarget = avg3mTarget;
    }

    /**
     * 
     * @return
     *     The avg3mTargetPct
     */
    public Double getAvg3mTargetPct() {
        return avg3mTargetPct;
    }

    /**
     * 
     * @param avg3mTargetPct
     *     The avg3mTargetPct
     */
    public void setAvg3mTargetPct(Double avg3mTargetPct) {
        this.avg3mTargetPct = avg3mTargetPct;
    }

    /**
     * 
     * @return
     *     The avg6mTarget
     */
    public Double getAvg6mTarget() {
        return avg6mTarget;
    }

    /**
     * 
     * @param avg6mTarget
     *     The avg6mTarget
     */
    public void setAvg6mTarget(Double avg6mTarget) {
        this.avg6mTarget = avg6mTarget;
    }

    /**
     * 
     * @return
     *     The avg6mTargetPct
     */
    public Double getAvg6mTargetPct() {
        return avg6mTargetPct;
    }

    /**
     * 
     * @param avg6mTargetPct
     *     The avg6mTargetPct
     */
    public void setAvg6mTargetPct(Double avg6mTargetPct) {
        this.avg6mTargetPct = avg6mTargetPct;
    }

    /**
     * 
     * @return
     *     The avg12mTarget
     */
    public Double getAvg12mTarget() {
        return avg12mTarget;
    }

    /**
     * 
     * @param avg12mTarget
     *     The avg12mTarget
     */
    public void setAvg12mTarget(Double avg12mTarget) {
        this.avg12mTarget = avg12mTarget;
    }

    /**
     * 
     * @return
     *     The avg12mTargetPct
     */
    public Double getAvg12mTargetPct() {
        return avg12mTargetPct;
    }

    /**
     * 
     * @param avg12mTargetPct
     *     The avg12mTargetPct
     */
    public void setAvg12mTargetPct(Double avg12mTargetPct) {
        this.avg12mTargetPct = avg12mTargetPct;
    }

    /**
     * 
     * @return
     *     The currentPrice
     */
    public Double getCurrentPrice() {
        return currentPrice;
    }

    public String getCurrentPriceString() {
        return GlobalUtils.NumberToString(currentPrice);
    }
    /**
     * 
     * @param currentPrice
     *     The currentPrice
     */
    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    /**
     * 
     * @return
     *     The direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 
     * @param direction
     *     The direction
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * 
     * @return
     *     The prevScore
     */
    public Double getPrevScore() {
        return prevScore;
    }

    /**
     * 
     * @param prevScore
     *     The prev_score
     */
    public void setPrevScore(Double prevScore) {
        this.prevScore = prevScore;
    }

    /**
     * 
     * @return
     *     The totalStrongSellRatings
     */
    public Long getTotalStrongSellRatings() {
        return totalStrongSellRatings;
    }

    /**
     * 
     * @param totalStrongSellRatings
     *     The totalStrongSellRatings
     */
    public void setTotalStrongSellRatings(Long totalStrongSellRatings) {
        this.totalStrongSellRatings = totalStrongSellRatings;
    }

    /**
     * 
     * @return
     *     The totalSellRatings
     */
    public Long getTotalSellRatings() {
        return totalSellRatings;
    }

    /**
     * 
     * @param totalSellRatings
     *     The totalSellRatings
     */
    public void setTotalSellRatings(Long totalSellRatings) {
        this.totalSellRatings = totalSellRatings;
    }

    /**
     * 
     * @return
     *     The totalHoldRatings
     */
    public Long getTotalHoldRatings() {
        return totalHoldRatings;
    }

    /**
     * 
     * @param totalHoldRatings
     *     The totalHoldRatings
     */
    public void setTotalHoldRatings(Long totalHoldRatings) {
        this.totalHoldRatings = totalHoldRatings;
    }

    /**
     * 
     * @return
     *     The totalBuyRatings
     */
    public Long getTotalBuyRatings() {
        return totalBuyRatings;
    }

    /**
     * 
     * @param totalBuyRatings
     *     The totalBuyRatings
     */
    public void setTotalBuyRatings(Long totalBuyRatings) {
        this.totalBuyRatings = totalBuyRatings;
    }

    /**
     * 
     * @return
     *     The totalStrongBuyRatings
     */
    public Long getTotalStrongBuyRatings() {
        return totalStrongBuyRatings;
    }

    /**
     * 
     * @param totalStrongBuyRatings
     *     The totalStrongBuyRatings
     */
    public void setTotalStrongBuyRatings(Long totalStrongBuyRatings) {
        this.totalStrongBuyRatings = totalStrongBuyRatings;
    }

    /**
     * 
     * @return
     *     The arnTargetPrice
     */
    public Double getArnTargetPrice() {
        return arnTargetPrice;
    }

    public String getArnTargetPriceString() {
        return GlobalUtils.NumberToString(arnTargetPrice);
    }
    /**
     * 
     * @param arnTargetPrice
     *     The arnTargetPrice
     */
    public void setArnTargetPrice(Double arnTargetPrice) {
        this.arnTargetPrice = arnTargetPrice;
    }

    /**
     * 
     * @return
     *     The valid
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * 
     * @param valid
     *     The valid
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

}
