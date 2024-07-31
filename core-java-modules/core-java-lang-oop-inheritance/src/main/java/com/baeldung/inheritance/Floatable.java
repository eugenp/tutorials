package com.baeldung.inheritance;

public interface Floatable {
    int duration = 10;
    void floatOnWater();
    
    default void repair() {
        System.out.println("Repairing Floatable object");   
    }
}
