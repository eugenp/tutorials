package com.baeldung.shallowvsdeepcopy;

public class DeepHouse implements Cloneable {

    private String address;
    private HouseLayout layout;

    public DeepHouse(String address, HouseLayout layout) {
        this.address = address;
        this.layout = layout;
    }

    @Override
    public DeepHouse clone() throws CloneNotSupportedException {
        DeepHouse cloned = (DeepHouse) super.clone();
        cloned.setLayout(cloned.getLayout()
          .clone());
        return cloned;
    }

    public HouseLayout getLayout() {
        return layout;
    }

    public void setLayout(HouseLayout layout) {
        this.layout = layout;
    }

}
