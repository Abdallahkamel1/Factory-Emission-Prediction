package com.example.factory_emission.models;

public class Operator {
    String operatorID;
    String factoryID;
    String operatorName;
    String operatorEmail;
    String operatorPhone;
    String operatorPassword;

    public Operator() {
    }

    public Operator(String operatorID, String factoryID, String operatorName, String operatorEmail, String operatorPhone, String operatorPassword) {
        this.operatorID = operatorID;
        this.factoryID = factoryID;
        this.operatorName = operatorName;
        this.operatorEmail = operatorEmail;
        this.operatorPhone = operatorPhone;
        this.operatorPassword = operatorPassword;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public String getFactoryID() {
        return factoryID;
    }

    public void setFactoryID(String factoryID) {
        this.factoryID = factoryID;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getOperatorPhone() {
        return operatorPhone;
    }

    public void setOperatorPhone(String operatorPhone) {
        this.operatorPhone = operatorPhone;
    }

    public String getOperatorPassword() {
        return operatorPassword;
    }

    public void setOperatorPassword(String operatorPassword) {
        this.operatorPassword = operatorPassword;
    }
}
