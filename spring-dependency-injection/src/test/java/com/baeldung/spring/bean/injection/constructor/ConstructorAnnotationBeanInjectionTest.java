package com.baeldung.spring.bean.injection.constructor;

import com.baeldung.spring.bean.injection.AbstractApplicationAnnotation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ConstructorAnnotationBeanInjectionTest extends AbstractApplicationAnnotation {

        @Autowired private ConstructorAnnotationBeanInjection constructorAnnotationBeanInjection;

        @Test public void testConstructorAnnotationBeanInjectionRead() {
                assertEquals("Reached read value", constructorAnnotationBeanInjection.readData());
        }

        @Test public void testConstructorAnnotationBeanInjectionWrite() {
                assertEquals("test", constructorAnnotationBeanInjection.writeData("test"));
        }

}