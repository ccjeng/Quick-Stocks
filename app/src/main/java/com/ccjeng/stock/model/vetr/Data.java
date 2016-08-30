package com.ccjeng.stock.model.vetr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("security")
    @Expose
    private Security security;
    @SerializedName("heldByCount")
    @Expose
    private Long heldByCount;
    @SerializedName("aggratingcalc")
    @Expose
    private Aggratingcalc aggratingcalc;

    /**
     * 
     * @return
     *     The security
     */
    public Security getSecurity() {
        return security;
    }

    /**
     * 
     * @param security
     *     The security
     */
    public void setSecurity(Security security) {
        this.security = security;
    }

    /**
     * 
     * @return
     *     The heldByCount
     */
    public Long getHeldByCount() {
        return heldByCount;
    }

    /**
     * 
     * @param heldByCount
     *     The heldByCount
     */
    public void setHeldByCount(Long heldByCount) {
        this.heldByCount = heldByCount;
    }

    /**
     * 
     * @return
     *     The aggratingcalc
     */
    public Aggratingcalc getAggratingcalc() {
        return aggratingcalc;
    }

    /**
     * 
     * @param aggratingcalc
     *     The aggratingcalc
     */
    public void setAggratingcalc(Aggratingcalc aggratingcalc) {
        this.aggratingcalc = aggratingcalc;
    }

}
