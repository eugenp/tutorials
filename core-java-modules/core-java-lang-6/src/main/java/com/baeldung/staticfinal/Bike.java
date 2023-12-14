package com.baeldung.staticfinal;

import java.util.HashMap;

public class Bike {
    public static final int TIRE = 2;
    public static final int PEDAL;
    public static final HashMap<String, Integer> PART = new HashMap<>();

    static {
        PEDAL = 5;
    }

}
