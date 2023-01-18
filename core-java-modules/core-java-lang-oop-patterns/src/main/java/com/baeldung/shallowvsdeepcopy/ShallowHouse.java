package com.baeldung.shallowvsdeepcopy;

public class ShallowHouse implements Cloneable {

    private String address;
    private HouseLayout layout;

    public ShallowHouse(String address, HouseLayout layout) {
        this.address = address;
        this.layout = layout;
    }

    @Override
    public ShallowHouse clone() throws CloneNotSupportedException {
        return (ShallowHouse) super.clone();
    }

    public HouseLayout getLayout() {
        return layout;
    }

}
