package com.example.factory_emission.models;

public class Prediction {
    String strPredictID;
    String strPredictState;
    String strPredictDate;
    String strCOLevel;
    String strSOLevel;
    String strNOLevel;
    String strPMLevel;
    String strFactoryID;

    public Prediction() {
    }

    public Prediction(String strPredictID, String strPredictState, String strPredictDate, String strCOLevel, String strSOLevel, String strNOLevel, String strPMLevel, String strFactoryID) {
        this.strPredictID = strPredictID;
        this.strPredictState = strPredictState;
        this.strPredictDate = strPredictDate;
        this.strCOLevel = strCOLevel;
        this.strSOLevel = strSOLevel;
        this.strNOLevel = strNOLevel;
        this.strPMLevel = strPMLevel;
        this.strFactoryID = strFactoryID;
    }

    public String getStrPredictID() {
        return strPredictID;
    }

    public void setStrPredictID(String strPredictID) {
        this.strPredictID = strPredictID;
    }

    public String getStrPredictState() {
        return strPredictState;
    }

    public void setStrPredictState(String strPredictState) {
        this.strPredictState = strPredictState;
    }

    public String getStrPredictDate() {
        return strPredictDate;
    }

    public void setStrPredictDate(String strPredictDate) {
        this.strPredictDate = strPredictDate;
    }

    public String getStrCOLevel() {
        return strCOLevel;
    }

    public void setStrCOLevel(String strCOLevel) {
        this.strCOLevel = strCOLevel;
    }

    public String getStrSOLevel() {
        return strSOLevel;
    }

    public void setStrSOLevel(String strSOLevel) {
        this.strSOLevel = strSOLevel;
    }

    public String getStrNOLevel() {
        return strNOLevel;
    }

    public void setStrNOLevel(String strNOLevel) {
        this.strNOLevel = strNOLevel;
    }

    public String getStrPMLevel() {
        return strPMLevel;
    }

    public void setStrPMLevel(String strPMLevel) {
        this.strPMLevel = strPMLevel;
    }

    public String getStrFactoryID() {
        return strFactoryID;
    }

    public void setStrFactoryID(String strFactoryID) {
        this.strFactoryID = strFactoryID;
    }
}
