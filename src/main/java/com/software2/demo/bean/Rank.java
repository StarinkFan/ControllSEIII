package com.software2.demo.bean;

public class Rank {
    private String userID;
    private int earnCredit;
    private int rank;

    public Rank(){}
    public Rank(String userID, int earnCredit, int rank) {
        this.userID = userID;
        this.earnCredit = earnCredit;
        this.rank = rank;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getEarnCredit() {
        return earnCredit;
    }

    public void setEarnCredit(int earnCredit) {
        this.earnCredit = earnCredit;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
