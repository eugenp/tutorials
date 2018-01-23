package com.baeldung.di.types.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanDependencyTypesXMLTest {

    @Test
    public void whenProperty_thenInjectionValid() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com.baeldung.di.types.xml");
        Author author = appContext.getBean(Author.class);

        assertNotNull(author);
        assertEquals("Java Champion", author.getName());

    }

    @Test
    public void whenConstructor_thenInjectionValid() {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("com.baeldung.di.types.xml");
        Book book = appContext.getBean(Book.class);

        assertNotNull(book);
        assertEquals("111222333", book.getIsbn());
        Author author = book.getAuthor();
        assertNotNull(author);
        assertEquals("Java Champion", author.getName());

    }
}
