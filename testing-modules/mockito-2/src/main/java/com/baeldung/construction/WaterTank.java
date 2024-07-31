package com.baeldung.construction;

public class WaterTank {

    private int mils;

    public WaterTank() {
        this.mils = 25;
    }
    
    public WaterTank(int mils) {
        this.mils = mils;
    }

    public int getMils() {
        return mils;
    }

    public void setMils(int mils) {
        this.mils = mils;
    }

    public boolean isEspresso() {
        return getMils() < 50;
    }

}
