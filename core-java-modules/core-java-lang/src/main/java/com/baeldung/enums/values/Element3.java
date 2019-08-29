package com.baeldung.enums.values;

import java.util.HashMap;
import java.util.Map;

/**
 * A Map has been added to cache labels for faster lookup.
 */
public enum Element3 {
    H("Hydrogen"),
    HE("Helium"),
    LI("Lithium"),
    BE("Beryllium"),
    B("Boron"),
    C("Carbon"),
    N("Nitrogen"),
    O("Oxygen"),
    F("Flourine"),
    NE("Neon");

    /** 
     * A map to cache labels and their associated Element3 instances.
     * Note that this only works if the labels are all unique!
     */
    private static final Map<String, Element3> BY_LABEL = new HashMap<>();
    
    /** populate the BY_LABEL cache */
    static {
        for (Element3 e3 : values()) {
            BY_LABEL.put(e3.label, e3);
        }
    }

    /** a final variable to store the label, which can't be changed */
    public final String label;

    /**
     * A private constructor that sets the label.
     * @param label 
     */
    private Element3(String label) {
        this.label = label;
    }

    /**
     * Look up Element2 instances by the label field. This implementation finds the
     * label in the BY_LABEL cache.
     * @param label The label to look up
     * @return The Element3 instance with the label, or null if not found.
     */
    public static Element3 valueOfLabel(String label) {
        return BY_LABEL.get(label);
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
