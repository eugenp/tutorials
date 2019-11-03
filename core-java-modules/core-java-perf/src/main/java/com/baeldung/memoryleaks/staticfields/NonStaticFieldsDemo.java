package com.baeldung.memoryleaks.staticfields;

import java.util.ArrayList;
import java.util.List;

public class NonStaticFieldsDemo {
    public List<Double> list = new ArrayList<>();
 
    public void populateList() {
        for (int i = 0; i < 10000000; i++) {
            list.add(Math.random());
        }
        System.out.println("Debug Point 2");
    }
 
    public static void main(String[] args) {
        System.out.println("Debug Point 1");
        new NonStaticFieldsDemo().populateList();
        System.out.println("Debug Point 3");
    }
}
