package com.baeldung.classloader;

import com.sun.javafx.util.Logging;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class PrintClassLoader {

    public void printClassLoaders() throws ClassNotFoundException {

        System.out.println("Classloader of this class:" + PrintClassLoader.class.getClassLoader());
        System.out.println("Classloader of Logging:" + Logging.class.getClassLoader());
        System.out.println("Classloader of ArrayList:" + ArrayList.class.getClassLoader());

    }
}
