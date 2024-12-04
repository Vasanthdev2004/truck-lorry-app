
package com.ruru.routelorry.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class NotificationDatum {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("rid")
    @Expose
    private String rid;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("date")
    @Expose
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}