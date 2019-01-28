package com.baeldung.inheritance;

/**
 * 宜于飞行的；适航的；可以飞行的；可驾驶的
 * @author zn.wang
 */
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
