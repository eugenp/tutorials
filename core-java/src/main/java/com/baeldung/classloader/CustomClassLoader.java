package com.baeldung.classloader;

import java.io.*;

/**
 * @author zn.wang
 */
public class CustomClassLoader extends ClassLoader {

    public Class getClass(String name) throws ClassNotFoundException {
        byte[] b = loadClassFromFile(name);
        return defineClass(name, b, 0, b.length);
    }

    /**
     * 从文件中加载类
     * @param fileName
     * @return
     */
    private byte[] loadClassFromFile(String fileName)  {
        InputStream inputStream =
                getClass()
                        .getClassLoader()
                        .getResourceAsStream(fileName.replace('.', File.separatorChar) + ".class");
        byte[] buffer;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int nextValue = 0;
        try {
            while ( (nextValue = inputStream.read()) != -1 ) {
                byteStream.write(nextValue);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        buffer = byteStream.toByteArray();
        return buffer;
    }
}
