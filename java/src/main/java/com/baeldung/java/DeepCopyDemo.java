package com.baeldung.java;

/**
 * Class to demonstrate the deep copy mechanism
 */
public class DeepCopyDemo {
    public static void main(String[] args) {
        SimpleDemoClass instance = new SimpleDemoClass();
        System.out.println("Original Instance Name: " + instance.getName());

        SimpleDemoClass deepCopyInstance = new SimpleDemoClass();
        deepCopyInstance.setName(instance.getName());
        deepCopyInstance.setInteger(instance.getInteger());
        deepCopyInstance.setFlag(instance.getFlag());

        System.out.println("Deep Copied Instance Name before change: " + deepCopyInstance);

        deepCopyInstance.setName("Baeldung_deep_copy_instance");

        System.out.println("Deep Copied Instance Name after change: " + deepCopyInstance.getName());
        System.out.println("Original Instance Name after change: " + instance.getName());

    }
}
