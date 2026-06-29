package com.example.factory_emission.models;

public class Factory {
    String factoryID;
    String factoryName;
    String factoryCity;
    String industryType;
    String fuelType;
    String factoryLatitude;
    String factoryLongitude;

    public Factory() {
    }

    public Factory(String factoryID, String factoryName, String factoryCity, String industryType, String fuelType, String factoryLatitude, String factoryLongitude) {
        this.factoryID = factoryID;
        this.factoryName = factoryName;
        this.factoryCity = factoryCity;
        this.industryType = industryType;
        this.fuelType = fuelType;
        this.factoryLatitude = factoryLatitude;
        this.factoryLongitude = factoryLongitude;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getFactoryCity() {
        return factoryCity;
    }

    public void setFactoryCity(String factoryCity) {
        this.factoryCity = factoryCity;
    }

    public String getIndustryType() {
        return industryType;
    }

    public void setIndustryType(String industryType) {
        this.industryType = industryType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getFactoryLatitude() {
        return factoryLatitude;
    }

    public void setFactoryLatitude(String factoryLatitude) {
        this.factoryLatitude = factoryLatitude;
    }

    public String getFactoryLongitude() {
        return factoryLongitude;
    }

    public void setFactoryLongitude(String factoryLongitude) {
        this.factoryLongitude = factoryLongitude;
    }
}
