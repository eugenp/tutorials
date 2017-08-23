package com.baeldung.spring.bean.injection.setter;

import com.baeldung.spring.bean.injection.AbstractApplicationAnnotation;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SetterAnnotationBeanInjectionTest extends AbstractApplicationAnnotation {

        @Autowired
        private SetterAnnotationBeanInjection setterAnnotationBeanInjection;

        @Test public void testConstructorAnnotationBeanInjectionRead() {
                assertEquals("Reached read value", setterAnnotationBeanInjection.readData());
        }

        @Test public void testConstructorAnnotationBeanInjectionWrite() {
                assertEquals("test", setterAnnotationBeanInjection.writeData("test"));
        }

}