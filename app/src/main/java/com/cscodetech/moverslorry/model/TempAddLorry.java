package com.ruru.routelorry.model;

import java.io.Serializable;

public class TempAddLorry implements Serializable {

    private String recordId;

    private String ownerId;

    private String lorryNo;

    private String weight;

    private String description;

    private String vehicleId;

    private String status;

    private String currLocation;

    private String currStateId;

    private String routes;
    private String route[];

    private String size;

    private String image0;


    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLorryNo() {
        return lorryNo;
    }

    public void setLorryNo(String lorryNo) {
        this.lorryNo = lorryNo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrLocation() {
        return currLocation;
    }

    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    public String getCurrStateId() {
        return currStateId;
    }

    public void setCurrStateId(String currStateId) {
        this.currStateId = currStateId;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImage0() {
        return image0;
    }

    public void setImage0(String image0) {
        this.image0 = image0;
    }

    public String[] getRoute() {
        return route;
    }

    public void setRoute(String[] route) {
        this.route = route;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
}