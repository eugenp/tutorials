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

public MedicalTestResult(String testName, String testID, double testPrice){
    this.testName = testName;
    this.testID = testID;
    this.testPrice = testPrice;
}

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalTestResult that = (MedicalTestResult) o;
        return Double.compare(testPrice, that.testPrice) == 0 && Objects.equals(testName, that.testName) && Objects.equals(testID, that.testID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testName, testID, testPrice);
    }

//    @Override
//    public String toString() {
//        return "MedicalTestResult{" + "test name='" + testName + "' test id='" + testID + "' test price='" + testPrice + "' }";
//    }
}
