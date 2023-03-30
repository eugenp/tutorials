package com.baeldung.java.copy;

import java.util.HashMap;
import java.util.Map;

public class DeepCopy implements Cloneable {

    public int primitive;
    public String immutable;
    public Map<String, String> mapObject = new HashMap<>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        DeepCopy copy = new DeepCopy();
        copy.primitive = primitive;
        copy.immutable = immutable;
        copy.mapObject = new HashMap<>(mapObject);
        return copy;
    }

    @Override
    public String toString() {
        return "DeepCopy{" + "primitive=" + primitive + ", immutable='" + immutable + '\'' + ", mapObject=" + mapObject + '}';
    }
}
