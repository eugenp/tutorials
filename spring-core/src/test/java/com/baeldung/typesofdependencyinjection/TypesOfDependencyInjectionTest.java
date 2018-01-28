package com.baeldung.typesofdependencyinjection;


import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypesOfDependencyInjectionTest {

    private ApplicationContext applicationContext;

    @Before
    public void setup(){
        applicationContext = new ClassPathXmlApplicationContext("typesofdependencyinjection-context.xml");
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnConstructor_ThenDependencyValid(){

        // GIVEN

        LibraryServiceConstructorInjection constructorInjection
                = (LibraryServiceConstructorInjection) applicationContext.getBean("libraryServiceConstructorInjection");
        String actualBook = constructorInjection.getNewBook("Effective Java");

        // THEN ASSERT

        String expectedBook = "book Effective Java issued";
        assertThat(actualBook, is(equalTo(expectedBook)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnSetter_ThenDependencyValid(){

        // GIVEN

        LibraryServiceConstructorInjection constructorInjection
                = (LibraryServiceConstructorInjection) applicationContext.getBean("libraryServiceConstructorInjection");
        String actualBook = constructorInjection.getNewBook("Spring in Action");

        // THEN ASSERT

        String expectedBook = "book Spring in Action issued";
        assertThat(actualBook, is(equalTo(expectedBook)));
    }

    @Test
    public void givenAutowiredAnnotation_WhenSetOnField_ThenDependencyValid(){

        // GIVEN

        LibraryServiceConstructorInjection constructorInjection
                = (LibraryServiceConstructorInjection) applicationContext.getBean("libraryServiceConstructorInjection");
        String actualBook = constructorInjection.getNewBook("Spring-Microservices in Action");

        // THEN ASSERT

        String expectedBook = "book Spring-Microservices in Action issued";
        assertThat(actualBook, is(equalTo(expectedBook)));
    }
}
