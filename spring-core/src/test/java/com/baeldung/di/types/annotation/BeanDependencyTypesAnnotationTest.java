package com.baeldung.di.types.annotation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDependencyTypesAnnotationTest {

    @Test
    public void whenAutowiredSetOnSetter_thenInjectionValid() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Author author = appContext.getBean(Author.class);

        assertNotNull(author);
        assertEquals("Java Champion", author.getName());

    }

    @Test
    public void whenAutowiredSetOnConstructor_thenInjectionValid() {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(AppConfig.class);
        Book book = appContext.getBean(Book.class);

        assertNotNull(book);

        Author author = book.getAuthor();
        assertNotNull(author);
        assertEquals("Java Champion", author.getName());

    }
}
