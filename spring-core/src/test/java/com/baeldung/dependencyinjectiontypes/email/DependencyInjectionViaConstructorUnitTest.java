package com.baeldung.dependencyinjectiontypes.email;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DependencyInjectionViaConstructorUnitTest {

    @Test
    public void givenXMLContext_whenInjectionViaConstructor_thenBeanIsNotNull(){
        //Given
        ApplicationContext contextFromXML = new ClassPathXmlApplicationContext("dependencyinjectiontypes-email-example-context.xml");
        //When
        ViaConstructorEmailExample result = contextFromXML.getBean(
                "viaConstructorEmailExample",
                ViaConstructorEmailExample.class);
        //Then
        assertThat(result, is(notNullValue()));
    }

    @Test
    public void givenBeanContext_whenInjectionViaConstructor_thenBeanIsNotNull(){
        //Given
        ApplicationContext contextFromBean = new AnnotationConfigApplicationContext(EmailConfiguration.class);
        //When
        ViaConstructorEmailExample result = contextFromBean.getBean(
                "viaConstructorEmailExample",
                ViaConstructorEmailExample.class);
        //Then
        assertThat(result, is(notNullValue()));
    }


}
