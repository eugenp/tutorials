package com.baeldung.resource;

import java.net.URL;

public class ClassLoaderGetResourceExample {

    public static void main(String[] args) {
        URL resourceAbsolutePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("com/baeldung/resource/example.txt");
        System.out.println("Resource with absolute path = " + resourceAbsolutePath);

        URL resourceRelativePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("example.txt");
        System.out.println("Resource with relative path = " + resourceRelativePath);

    }
}
