package com.baeldung.classloader;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 用户自定义的ClassLoader
 * @see java.lang.ClassLoader#getClass();
 */
public class CustomClassLoaderUnitTest {

    @Test
    public void customLoader() throws
                   ClassNotFoundException,
                   IllegalAccessException,
                   InstantiationException,
                   NoSuchMethodException,
                   InvocationTargetException {

        CustomClassLoader customClassLoader = new CustomClassLoader();
        Class<?> c = customClassLoader.getClass(PrintClassLoader.class.getName());

        Object ob = c.newInstance();

        Method md = c.getMethod("printClassLoaders");
        md.invoke(ob);

    }

}
