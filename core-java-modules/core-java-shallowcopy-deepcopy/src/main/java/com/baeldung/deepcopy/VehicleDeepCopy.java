package deepcopy;

import utils.Engine;

public class VehicleDeepCopy implements Cloneable {
    public String name;
    public Engine engine;

    public VehicleDeepCopy(String name, Engine engine) {
        this.name = name;
        this.engine = engine;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        VehicleDeepCopy vehicleDeepCopyClone = (VehicleDeepCopy) super.clone();
        vehicleDeepCopyClone.engine = (Engine) engine.clone();
        return vehicleDeepCopyClone;
    }
}
