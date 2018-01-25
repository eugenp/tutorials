package com.baeldung.beaninjectiontypes.domian;

public class GraphicsCard {

    private int ram;

    private String name;

    public GraphicsCard(int ram, String name) {
        super();
        this.ram = ram;
        this.name = name;
    }

    public int getRam() {
        return ram;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "GraphicsCard [ram=" + ram + ", name=" + name + "]";
    }

}
