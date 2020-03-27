package com.baeldung.architecturalpattern.dao;

public class Covid {

    private String countryName;

    private int noOfActiveCases;

    private int noOfRecoverdCases;

    private int noOfFatalCases;

    public Covid(String countryName, int noOfActiveCases, int noOfRecoverdCases, int noOfFatalCases) {
        this.countryName = countryName;
        this.noOfActiveCases = noOfActiveCases;
        this.noOfRecoverdCases = noOfRecoverdCases;
        this.noOfFatalCases = noOfFatalCases;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getNoOfActiveCases() {
        return noOfActiveCases;
    }

    public void setNoOfActiveCases(int noOfActiveCases) {
        this.noOfActiveCases = noOfActiveCases;
    }

    public int getNoOfRecoverdCases() {
        return noOfRecoverdCases;
    }

    public void setNoOfRecoverdCases(int noOfRecoverdCases) {
        this.noOfRecoverdCases = noOfRecoverdCases;
    }

    public int getNoOfFatalCases() {
        return noOfFatalCases;
    }

    public void setNoOfFatalCases(int noOfFatalCases) {
        this.noOfFatalCases = noOfFatalCases;
    }

    public int getTotalCases() {
        return noOfActiveCases + noOfRecoverdCases + noOfFatalCases;
    }

}
