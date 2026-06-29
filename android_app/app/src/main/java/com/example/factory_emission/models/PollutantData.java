package com.example.factory_emission.models;

public class PollutantData {
    String pollID;
    String pollName;
    String pollSafe;

    public PollutantData() {
    }

    public PollutantData(String pollID, String pollName, String pollSafe) {
        this.pollID = pollID;
        this.pollName = pollName;
        this.pollSafe = pollSafe;
    }

    public String getPollID() {
        return pollID;
    }

    public void setPollID(String pollID) {
        this.pollID = pollID;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getPollSafe() {
        return pollSafe;
    }

    public void setPollSafe(String pollSafe) {
        this.pollSafe = pollSafe;
    }
}
