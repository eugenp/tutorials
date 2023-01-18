package com.baeldung.shallowvsdeepcopy;

public class HouseLayout implements Cloneable {

    private int floors;
    private int bedrooms;

    public HouseLayout(int floors, int bedrooms) {
        this.floors = floors;
        this.bedrooms = bedrooms;
    }

    @Override
    public HouseLayout clone() throws CloneNotSupportedException {
        return (HouseLayout) super.clone();
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

}
