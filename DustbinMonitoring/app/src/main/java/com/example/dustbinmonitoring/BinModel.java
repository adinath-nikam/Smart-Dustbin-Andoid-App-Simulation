package com.example.dustbinmonitoring;

class BinModel {
    String BinValue, BinId, Lat, Lon;

    public String getBinId() {
        return BinId;
    }

    public String getLat() {
        return Lat;
    }

    public String getLon() {
        return Lon;
    }

    public String getBinValue() {
        return BinValue;
    }

    public void setBinId(String binId) {
        BinId = binId;
    }

    public void setBinValue(String binValue) {
        BinValue = binValue;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public void setLon(String lon) {
        Lon = lon;
    }
    public BinModel(){}

    public BinModel(String binId, String binValue, String lat, String lon){
        this.BinId = binId;
        this.BinValue = binValue;
        this.Lat = lat;
        this.Lon = lon;

    }
}