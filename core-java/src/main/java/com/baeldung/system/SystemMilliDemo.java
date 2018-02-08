package com.baeldung.system;

import java.util.Date;

public class SystemMilliDemo {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis()); // Unix epoch in milli-seconds
        System.out.println(new Date(System.currentTimeMillis())); // human understandable format
    }
}
