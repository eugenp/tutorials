package com.baeldung.loadedclasslisting.customLoader;

import java.util.ArrayList;

public class ClassLoaderInfo {

    public void printClassLoaders() throws ClassNotFoundException {

        System.out.println("Classloader of this class:" + ClassLoaderInfo.class.getClassLoader());
        System.out.println("Classloader of ArrayList:" + ArrayList.class.getClassLoader());
    }
}
