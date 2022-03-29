package com.baeldung.java;

/**
 * Class to demonstrate the shallow copy mechanism
 */
public class ShallowCopyDemo {

    public static void main(String[] args) {
        SimpleDemoClass instance = new SimpleDemoClass();
        System.out.println("Original Instance Name: " + instance.getName());

        SimpleDemoClass shallowCopyInstance = instance;
        System.out.println("Shallow Copied Instance Name before change: " + shallowCopyInstance.getName());

        shallowCopyInstance.setName("Baeldung_shallow_copy");

        System.out.println("Shallow Copied Instance Name after change: " + shallowCopyInstance.getName());
        System.out.println("Original Instance Name after change: " + instance.getName());

    }
}
