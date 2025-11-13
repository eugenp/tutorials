package com.baeldung.enums.values;

import java.util.Objects;

/**
 * The simple enum has been enhanced to add the name of the element.
 */
public enum Element2 {
    H("Hydrogen"),
    HE("Helium"),
    LI("Lithium"),
    BE("Beryllium"),
    B("Boron"),
    C("Carbon"),
    N("Nitrogen"),
    O("Oxygen"),
    // Fixed: "Flourine" -> "Fluorine"
    F("Fluorine"), 
    NE("Neon"),
    // Added: Dedicated UNKNOWN member
    UNKNOWN("Unknown Element"); 

    /** a final variable to store the label, which can't be changed */
    public final String label;

    /**
     * A private constructor that sets the label.
     * @param label 
     */
    private Element2(String label) {
        this.label = label;
    }

    /**
     * Look up Element2 instances by the label field. This implementation iterates through 
     * the values() list to find the label.
     * @param label The label to look up
     * @return The Element2 instance with the label, or UNKNOWN if not found.
     */
    public static Element2 valueOfLabel(String label) {
        for (Element2 e2 : values()) {
            // Fixed: Used Objects.equals for null-safe comparison
            if (Objects.equals(e2.label, label)) {
                return e2;
            }
        }
        // Fixed: Return UNKNOWN instead of null
        return UNKNOWN; 
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
