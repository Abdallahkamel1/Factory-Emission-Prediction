package com.example.factory_emission.models;

public class OperatorData {
    String operatorID;
    String operatorName;
    String operatorEmail;
    String operatorPhone;
    String operatorFactory;

    public OperatorData() {
    }

    public OperatorData(String operatorID, String operatorName, String operatorEmail, String operatorPhone, String operatorFactory) {
        this.operatorID = operatorID;
        this.operatorName = operatorName;
        this.operatorEmail = operatorEmail;
        this.operatorPhone = operatorPhone;
        this.operatorFactory = operatorFactory;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
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

    public String getOperatorFactory() {
        return operatorFactory;
    }

    public void setOperatorFactory(String operatorFactory) {
        this.operatorFactory = operatorFactory;
    }
}
