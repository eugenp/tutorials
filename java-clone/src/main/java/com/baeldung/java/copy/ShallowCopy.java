package com.baeldung.java.copy;

import java.util.HashMap;
import java.util.Map;

public class ShallowCopy implements Cloneable {

    public int primitive;
    public String immutable;
    public Map<String, String> mapObject = new HashMap<>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "ShallowCopy{" + "primitive=" + primitive + ", immutable='" + immutable + '\'' + ", mapObject=" + mapObject + '}';
    }
}
