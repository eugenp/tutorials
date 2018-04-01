package com.coreservlets.demo3;

/**
 * Created by lihongjie on 6/30/17.
 */
public class Test {

    private static String string;

    public static void main(String[] args) {

        System.out.println("5" + 2); // 52

        String x = "hello";
        String y = "world";
        String z = new String("helloworld");
        String a = "helloworld";
        System.out.println("x+y equals z:" + (x + y).equals(z)); // true
        System.out.println("a == z:" + (a == z)); // false
        System.out.println("x == hello:" + (x == "hello")); // true
        System.out.println("a == helloworld:" + (a == "hello" + "world")); // true
        System.out.println("a == x+y:" + (a == (x + y)));
    }
}
