package com.baeldung.initializerblock.instanceblock;

public class InstanceBlockExample {

    {
        System.out.println("Instance initializer block 1");
    }
    
    {
        System.out.println("Instance initializer block 2");
    }
    
    public InstanceBlockExample() {
        System.out.println("Class constructor");
    }

    public static void main(String[] args) {
        InstanceBlockExample iib = new InstanceBlockExample();
        System.out.println("Main Method");
    }
}

