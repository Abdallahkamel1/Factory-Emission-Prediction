package com.example.factory_emission.models;

public class Notes {
    String alertID;
    String factoryID;
    String alertDate;
    String emissionValue;
    String emissionMsg;

    public Notes() {
    }

    public Notes(String alertID, String factoryID, String alertDate, String emissionValue, String emissionMsg) {
        this.alertID = alertID;
        this.factoryID = factoryID;
        this.alertDate = alertDate;
        this.emissionValue = emissionValue;
        this.emissionMsg = emissionMsg;
    }

    public String getAlertID() {
        return alertID;
    }

    public void setAlertID(String alertID) {
        this.alertID = alertID;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getEmissionValue() {
        return emissionValue;
    }

    public void setEmissionValue(String emissionValue) {
        this.emissionValue = emissionValue;
    }

    public String getEmissionMsg() {
        return emissionMsg;
    }

    public void setEmissionMsg(String emissionMsg) {
        this.emissionMsg = emissionMsg;
    }
}
