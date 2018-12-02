package com.baeldung.inheritance;

public interface Flyable {
    int duration = 10;
    void fly();
    
    /*
     * Commented 
     */
    //default void repair() {
    //    System.out.println("Repairing Flyable object"); 
    //}
}
