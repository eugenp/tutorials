package com.baeldung.di.autowire.example.store;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.autowire.example.store.AppConfig;
import com.baeldung.di.autowire.example.store.Store;

public class UnitTest {

    private AnnotationConfigApplicationContext context;

    @Before
    public void setUp() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    /**
     *  Test dependency injection by setter
     */
    @Test
    public void getItemBySetterDIreturnBook() {
        Store store = context.getBean("store", Store.class);
        assertEquals(store.getSell()
            .getType(), "Book");
    }


}
