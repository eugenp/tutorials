package com.baeldung.spring.bean.injection.constructor;

import com.baeldung.spring.bean.injection.AbstractApplicationXml;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ConstructorXmlBeanInjectionTest extends AbstractApplicationXml {

        @Autowired private ConstructorXmlBeanInjection constructorXmlBeanInjection;

        @Test public void testConstructorAnnotationBeanInjectionRead() {
                assertEquals("Reached read value", constructorXmlBeanInjection.readData());
        }

        @Test public void testConstructorAnnotationBeanInjectionWrite() {
                assertEquals("test", constructorXmlBeanInjection.writeData("test"));
        }
}