package com.baeldung.copy.object;

public class Backpack {

    private int size;
    private LunchBox lunchBox;

    public Backpack(int size, LunchBox lunchBox) {
        this.size = size;
        this.lunchBox = lunchBox;
    }

    // standard getters and setters
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LunchBox getLunchBox() {
        return lunchBox;
    }

    public void setLunchBox(LunchBox lunchBox) {
        this.lunchBox = lunchBox;
    }
}
