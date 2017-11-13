package com.baeldung.dependencyinjectiontypes.email;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DependencyInjectionViaSetterUnitTest {

    @Test
    public void givenXMLContext_whenInjectionViaSetter_thenBeanIsNotNull(){
        //Given
        ApplicationContext contextFromXML = new ClassPathXmlApplicationContext("dependencyinjectiontypes-email-example-context.xml");
        //When
        ViaSetterEmailExample result = contextFromXML.getBean(
                "viaSetterEmailExample",
                ViaSetterEmailExample.class);
        //Then
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void givenBeanContext_whenInjectionViaSetter_thenBeanIsNotNull(){
        //Given
        ApplicationContext contextFromBean = new AnnotationConfigApplicationContext(EmailConfiguration.class);
        //When
        ViaSetterEmailExample result = contextFromBean.getBean(
                "viaSetterEmailExample",
                ViaSetterEmailExample.class);
        //Then
        assertThat(result, is(notNullValue()));
    }


}
