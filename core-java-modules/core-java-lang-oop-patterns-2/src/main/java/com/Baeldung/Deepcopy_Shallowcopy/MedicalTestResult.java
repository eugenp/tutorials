package com.Baeldung.Deepcopy_Shallowcopy;

import java.io.Serializable;
import java.util.Objects;

class MedicalTestResult implements Serializable,Cloneable {
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public double getTestPrice() {
        return testPrice;
    }

    public void setTestPrice(double testPrice) {
        this.testPrice = testPrice;
    }

    private String testName;
    private String testID;
    private double testPrice;

    public MedicalTestResult(String testName, String testID, double testPrice) {
        this.testName = testName;
        this.testID = testID;
        this.testPrice = testPrice;
    }

    @Override
    public Object clone() {
        try {
            return (MedicalTestResult) super.clone();
        } catch (CloneNotSupportedException e) {
            return new MedicalTestResult(this.testName, this.getTestID(), this.getTestPrice());
        }
    }

    @Override
    public String toString() {
        return "MedicalTestResult{" +
                "testName='" + testName + '\'' +
                ", testID='" + testID + '\'' +
                ", testPrice=" + testPrice +
                '}';
    }
}



