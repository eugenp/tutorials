package com.coreservlets.demo3;

/**
 * Created by lihongjie on 6/30/17.
 */
public class HelloA {
    public HelloA() {
        System.out.println("HelloA");
    }

    {
        System.out.println("I'm A class");
    }

    static {
        System.out.println("static A");
    }
}
