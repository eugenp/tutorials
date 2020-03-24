package com.baeldung.flightrecorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple program that illustrates how to use Java Flight Recorder.
 * 
 * This programs creates a list, inserts objects in it until 
 * an OutOfMemoryError is thrown.   
 *
 */
public class FlightRecorder {

    public static void main(String[] args) {
        List<Object> items = new ArrayList<>(1);
        try {
            while (true) {
                items.add(new Object());
            }
        } catch (OutOfMemoryError e) {
            System.out.println(e.getMessage());
        }
        assert items.size() > 0;
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

}
