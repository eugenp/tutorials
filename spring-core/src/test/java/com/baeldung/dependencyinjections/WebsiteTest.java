package com.baeldung.dependencyinjections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class WebsiteTest {

    ApplicationContext annotationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    ApplicationContext xmlContext = new ClassPathXmlApplicationContext("dependencyinjections-context.xml");

    public static final String EXPECTED_STRING = "Home page contents";

    @Test
    public void testAnnotationConfigConstructorBasedDI() {
        WebsiteControllerConstructorInjection controller = annotationContext.getBean(WebsiteControllerConstructorInjection.class);
        assertEquals(EXPECTED_STRING, controller.getContentsViaConstructorInjection());
    }

    @Test
    public void testXMLConfigConstructorBasedDI() {
        WebsiteControllerConstructorInjection controller = xmlContext.getBean(WebsiteControllerConstructorInjection.class);
        assertEquals(EXPECTED_STRING, controller.getContentsViaConstructorInjection());
    }

    @Test
    public void testAnnotationConfigSetterBasedDI() {
        WebsiteControllerSetterInjection controllerwithSetterInjection = (WebsiteControllerSetterInjection) annotationContext.getBean("websiteControllerSetterInjection");
        assertEquals(EXPECTED_STRING, controllerwithSetterInjection.getContentsViaSetterInjection());
    }

    @Test
    public void testXMLConfigSetterBasedDI() {
        WebsiteControllerSetterInjection controllerwithSetterInjection = (WebsiteControllerSetterInjection) xmlContext.getBean("websiteControllerSetterInjection");
        assertEquals(EXPECTED_STRING, controllerwithSetterInjection.getContentsViaSetterInjection());
    }

}
