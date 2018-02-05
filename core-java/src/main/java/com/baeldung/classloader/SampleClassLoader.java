package com.baeldung.classloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class SampleClassLoader {

       public void loadClass() throws ClassNotFoundException {

               System.out.println("Classloader of this class:"+SampleClassLoader.class.getClassLoader());

               Class.forName("com.baeldung.classloader.SampleClassLoader", true
                       ,  SampleClassLoader.class.getClassLoader().getParent());

       }


}
