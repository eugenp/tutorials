package com.baeldung.resource;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassLoaderGetResourceExample {

    private static Logger logger = LoggerFactory.getLogger(ClassLoaderGetResourceExample.class);

    public static void main(String[] args) {
        URL resourceAbsolutePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("com/baeldung/resource/example.txt");
        logger.info("Resource with absolute path = {}", resourceAbsolutePath);

        URL resourceRelativePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("example.txt");
        logger.info("Resource with relative path = {}", resourceRelativePath);
    }
}
