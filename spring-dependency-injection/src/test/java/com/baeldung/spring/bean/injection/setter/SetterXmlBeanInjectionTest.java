package com.baeldung.spring.bean.injection.setter;

import com.baeldung.spring.bean.injection.AbstractApplicationXml;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class SetterXmlBeanInjectionTest extends AbstractApplicationXml {

        @Autowired private SetterXmlBeanInjection setterXmlBeanInjection;

        @Test public void testConstructorAnnotationBeanInjectionRead() {
                assertEquals("Reached read value", setterXmlBeanInjection.readData());
        }

        @Test public void testConstructorAnnotationBeanInjectionWrite() {
                assertEquals("test", setterXmlBeanInjection.writeData("test"));
        }

}