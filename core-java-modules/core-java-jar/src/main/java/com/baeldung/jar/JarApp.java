package com.baeldung.jar;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class JarApp {

    public static String findObjectMapperClass() {
        Class<ObjectMapper> klass = ObjectMapper.class;
        URL path = klass.getProtectionDomain().getCodeSource().getLocation();
        return path.toString();
    }

    public static void main(String[] args) {
        System.out.println(findObjectMapperClass());
    }
}
