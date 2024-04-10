package shallowcopy;

import utils.Engine;

public class VehicleShallowCopy implements Cloneable {
    public String name;
    public Engine engine;

    public VehicleShallowCopy(String name, Engine engine) {
        this.name = name;
        this.engine = engine;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

