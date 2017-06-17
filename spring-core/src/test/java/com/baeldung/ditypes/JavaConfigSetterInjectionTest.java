package com.baeldung.ditypes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.ditypes.JavaConfig;
import com.baeldung.ditypes.domain.Book;

public class JavaConfigSetterInjectionTest {

    @Test
    public void givenJavaConfig_WhenSetterInjection_ThenValidDependency() {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);

        Book book = (Book) context.getBean("bookJavaSetter");
        assertEquals("Pride and Prejudice", book.getName());
        assertEquals("Classic", book.getCategory()
            .getName());
    }
}
