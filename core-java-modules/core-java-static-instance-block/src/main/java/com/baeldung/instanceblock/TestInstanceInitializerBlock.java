package com.baeldung.instanceblock;

public class TestInstanceInitializerBlock {

    {
        System.out.println("Instance initializer block 1");
    }
    {
        System.out.println("Instance initializer block 2");
    }
    public TestInstanceInitializerBlock() {
        System.out.println("Class constructor");
    }

    public static void main(String[] args) {
        TestInitializerBlock iib = new TestInitializerBlock();
        System.out.println("Main Method");
    }
}

