package com.baeldung.classloader;

import java.net.URL;
import java.net.URLClassLoader;

class CustomClassLoader extends URLClassLoader {

    CustomClassLoader(ClassLoader parent, URL... urls){
        super(urls,parent);
    }

    CustomClassLoader(URL... urls){
        super(urls,null);
    }
}