package com.ruru.routelorry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookLorry {
    @SerializedName("BookHistory")
    @Expose
    private List<LorrydataItem> bookHistory;
    @SerializedName("ResponseCode")
    @Expose
    private String responseCode;
    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("ResponseMsg")
    @Expose
    private String responseMsg;

    public List<LorrydataItem> getBookHistory() {
        return bookHistory;
    }

    public void setBookHistory(List<LorrydataItem> bookHistory) {
        this.bookHistory = bookHistory;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

}
