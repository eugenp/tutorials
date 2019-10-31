package com.baeldung.enums.values;

import java.util.HashMap;
import java.util.Map;

/**
 * Multiple fields have been added and the Labeled interface is implemented.
 */
public enum Element4 implements Labeled {
    H("Hydrogen", 1, 1.008f),
    HE("Helium", 2, 4.0026f),
    LI("Lithium", 3, 6.94f),
    BE("Beryllium", 4, 9.01722f),
    B("Boron", 5, 10.81f),
    C("Carbon", 6, 12.011f),
    N("Nitrogen", 7, 14.007f),
    O("Oxygen", 8, 15.999f),
    F("Flourine", 9, 18.998f),
    NE("Neon", 10, 20.180f);
    /** 
     * Maps cache labels and their associated Element3 instances.
     * Note that this only works if the values are all unique!
     */
    private static final Map<String, Element4> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Element4> BY_ATOMIC_NUMBER = new HashMap<>();
    private static final Map<Float, Element4> BY_ATOMIC_WEIGHT = new HashMap<>();
    
    /** populate the caches */
    static {
        for (Element4 e4 : values()) {
            BY_LABEL.put(e4.label, e4);
            BY_ATOMIC_NUMBER.put(e4.atomicNumber, e4);
            BY_ATOMIC_WEIGHT.put(e4.atomicWeight, e4);
        }
    }

    /** final variables to store the values, which can't be changed */
    public final String label;
    public final int atomicNumber;
    public final float atomicWeight;

    private Element4(String label, int atomicNumber, float atomicWeight) {
        this.label = label;
        this.atomicNumber = atomicNumber;
        this.atomicWeight = atomicWeight;
    }

    /**
     * Implement the Labeled interface.
     * @return the label value
     */
    @Override
    public String label() {
        return label;
    }

    /**
     * Look up Element2 instances by the label field. This implementation finds the
     * label in the BY_LABEL cache.
     * @param label The label to look up
     * @return The Element4 instance with the label, or null if not found.
     */
    public static Element4 valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    /**
     * Look up Element2 instances by the atomicNumber field. This implementation finds the
     * atomicNUmber in the cache.
     * @param number The atomicNumber to look up 
     * @return The Element4 instance with the label, or null if not found.
     */
    public static Element4 valueOfAtomicNumber(int number) {
        return BY_ATOMIC_NUMBER.get(number);
    }

    /**
     * Look up Element2 instances by the atomicWeight field. This implementation finds the
     * atomic weight in the cache.
     * @param weight the atomic weight to look up
     * @return The Element4 instance with the label, or null if not found.
     */
    public static Element4 valueOfAtomicWeight(float weight) {
        return BY_ATOMIC_WEIGHT.get(weight);
    }

    /**
     * Override the toString() method to return the label instead of the declared name.
     * @return 
     */
    @Override
    public String toString() {
        return this.label;
    }
}
