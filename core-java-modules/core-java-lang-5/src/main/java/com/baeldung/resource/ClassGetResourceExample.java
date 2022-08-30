package com.baeldung.resource;

import java.net.URL;

public class ClassGetResourceExample {

    public static void main(String[] args) {
        URL resourceAbsolutePath = ClassGetResourceExample.class.getResource("/com/baeldung/resource/example.txt");
        System.out.println("Resource with absolute path = " + resourceAbsolutePath);

        URL resourceRelativePath = ClassGetResourceExample.class.getResource("example.txt");
        System.out.println("Resource with relative path = " + resourceRelativePath);
    }
}