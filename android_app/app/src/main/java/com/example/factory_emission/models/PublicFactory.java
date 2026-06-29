package com.example.factory_emission.models;

public class PublicFactory {
    String companyName;
    String factoryName;
    String factoryCity;
    String factoryState;
    String stateYear;
    String stateCO;
    String stateSO;
    String stateNO;
    String statePM;
    String factoryIndustry;
    String factoryFuel;

    public PublicFactory() {
    }

    public PublicFactory(String companyName, String factoryName, String factoryCity, String factoryState, String stateYear, String stateCO, String stateSO, String stateNO, String statePM, String factoryIndustry, String factoryFuel) {
        this.companyName = companyName;
        this.factoryName = factoryName;
        this.factoryCity = factoryCity;
        this.factoryState = factoryState;
        this.stateYear = stateYear;
        this.stateCO = stateCO;
        this.stateSO = stateSO;
        this.stateNO = stateNO;
        this.statePM = statePM;
        this.factoryIndustry = factoryIndustry;
        this.factoryFuel = factoryFuel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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

    public String getFactoryState() {
        return factoryState;
    }

    public void setFactoryState(String factoryState) {
        this.factoryState = factoryState;
    }

    public String getStateYear() {
        return stateYear;
    }

    public void setStateYear(String stateYear) {
        this.stateYear = stateYear;
    }

    public String getStateCO() {
        return stateCO;
    }

    public void setStateCO(String stateCO) {
        this.stateCO = stateCO;
    }

    public String getStateSO() {
        return stateSO;
    }

    public void setStateSO(String stateSO) {
        this.stateSO = stateSO;
    }

    public String getStateNO() {
        return stateNO;
    }

    public void setStateNO(String stateNO) {
        this.stateNO = stateNO;
    }

    public String getStatePM() {
        return statePM;
    }

    public void setStatePM(String statePM) {
        this.statePM = statePM;
    }

    public String getFactoryIndustry() {
        return factoryIndustry;
    }

    public void setFactoryIndustry(String factoryIndustry) {
        this.factoryIndustry = factoryIndustry;
    }

    public String getFactoryFuel() {
        return factoryFuel;
    }

    public void setFactoryFuel(String factoryFuel) {
        this.factoryFuel = factoryFuel;
    }
}
