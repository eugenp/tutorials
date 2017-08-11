package com.baeldung.commons.chain;

import org.apache.commons.chain.impl.ContextBase;

public class AtmRequestContext extends ContextBase {

    int totalAmountToBeWithdrawn;
    int noOfHundredsDispensed;
    int noOfFiftiesDispensed;
    int noOfTensDispensed;
    int amountLeftToBeWithdrawn;

    public int getTotalAmountToBeWithdrawn() {
        return totalAmountToBeWithdrawn;
    }

    public void setTotalAmountToBeWithdrawn(int totalAmountToBeWithdrawn) {
        this.totalAmountToBeWithdrawn = totalAmountToBeWithdrawn;
    }

    public int getNoOfHundredsDispensed() {
        return noOfHundredsDispensed;
    }

    public void setNoOfHundredsDispensed(int noOfHundredsDispensed) {
        this.noOfHundredsDispensed = noOfHundredsDispensed;
    }

    public int getNoOfFiftiesDispensed() {
        return noOfFiftiesDispensed;
    }

    public void setNoOfFiftiesDispensed(int noOfFiftiesDispensed) {
        this.noOfFiftiesDispensed = noOfFiftiesDispensed;
    }

    public int getNoOfTensDispensed() {
        return noOfTensDispensed;
    }

    public void setNoOfTensDispensed(int noOfTensDispensed) {
        this.noOfTensDispensed = noOfTensDispensed;
    }

    public int getAmountLeftToBeWithdrawn() {
        return amountLeftToBeWithdrawn;
    }

    public void setAmountLeftToBeWithdrawn(int amountLeftToBeWithdrawn) {
        this.amountLeftToBeWithdrawn = amountLeftToBeWithdrawn;
    }
}
