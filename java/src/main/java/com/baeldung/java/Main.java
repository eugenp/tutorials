package com.baeldung.java;

public class Main {
    public static void main(String[] args) {
        SimpleDemoClass instance = new SimpleDemoClass();
        System.out.println("Actual object: " + instance);

        SimpleDemoClass shallowCopyInstance = instance;
        System.out.println("Shallow Copied Instance: " + shallowCopyInstance);

        SimpleDemoClass deepCopyInstance = new SimpleDemoClass();
        deepCopyInstance.setName(instance.getName());
        deepCopyInstance.setInteger(instance.getInteger());
        deepCopyInstance.setFlag(instance.getFlag());

        System.out.println("Deep Copied Instance: " + deepCopyInstance);

    }
}
