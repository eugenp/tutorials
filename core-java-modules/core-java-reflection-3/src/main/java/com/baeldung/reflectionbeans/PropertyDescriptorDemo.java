package com.baeldung.reflectionbeans;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class PropertyDescriptorDemo {

    public static void main(String[] args) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(Post.class);

        for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
            System.out.println(pd.getName());
        }

        Post post = new Post();
        PropertyDescriptor titlePd = new PropertyDescriptor("title", Post.class);

        Method write = titlePd.getWriteMethod();
        Method read = titlePd.getReadMethod();

        write.invoke(post, "Reflections in Java");
        String value = (String) read.invoke(post);

        System.out.println(value);
    }
}
