package com.baeldung.inheritance;

/**
 * 可漂浮的；可航行的；可浮选的
 * @author zn.wang
 */
public interface Floatable {
    int duration = 10;

    void floatOnWater();
    
    default void repair() {
        System.out.println("Repairing Floatable object");   
    }
}
