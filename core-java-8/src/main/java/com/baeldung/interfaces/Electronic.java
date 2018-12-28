package com.baeldung.interfaces;

public interface Electronic {
    //Constant variable
    public static final String LED = "LED";

    //Abstract method
    public int getElectricityUse();

    // Static method
    public static boolean isEnergyEfficient(String electtronicType) {
        if (electtronicType.equals(LED)) {
            return true;
        }
        return false;
    }

    //Default method
    public default void printDescription() {
        System.out.println("Electronic Description");
    }
}
