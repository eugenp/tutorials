package com.baeldung.jar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class App {

    public static String findObjectMapperClass() {
        Class klass = ObjectMapper.class;
        URL path = klass.getProtectionDomain().getCodeSource().getLocation();
        return path.toString();
    }

    public static void main(String[] args) {
        System.out.println(findObjectMapperClass());
    }
}
