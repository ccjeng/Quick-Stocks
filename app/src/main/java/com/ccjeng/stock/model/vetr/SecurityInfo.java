package com.ccjeng.stock.model.vetr;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SecurityInfo {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errorMessage")
    @Expose
    private String errorMessage;
    @SerializedName("totalRecords")
    @Expose
    private Long totalRecords;
    @SerializedName("hasMore")
    @Expose
    private Boolean hasMore;
    @SerializedName("sgn")
    @Expose
    private Boolean sgn;

    /**
     * 
     * @return
     *     The data
     */
    public Data getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The success
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * 
     * @param errorMessage
     *     The errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * 
     * @return
     *     The totalRecords
     */
    public Long getTotalRecords() {
        return totalRecords;
    }

    /**
     * 
     * @param totalRecords
     *     The totalRecords
     */
    public void setTotalRecords(Long totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * 
     * @return
     *     The hasMore
     */
    public Boolean getHasMore() {
        return hasMore;
    }

    /**
     * 
     * @param hasMore
     *     The hasMore
     */
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    /**
     * 
     * @return
     *     The sgn
     */
    public Boolean getSgn() {
        return sgn;
    }

    /**
     * 
     * @param sgn
     *     The sgn
     */
    public void setSgn(Boolean sgn) {
        this.sgn = sgn;
    }

}
