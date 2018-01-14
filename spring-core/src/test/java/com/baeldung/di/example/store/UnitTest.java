package com.baeldung.di.example.store;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.example.store.AppConfig;
import com.baeldung.di.example.store.Store;

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
        Store store = context.getBean("storeBook", Store.class);
        assertEquals(store.getSell()
            .getType(), "Book");
    }

    /**
     *  Test dependency injection by constructor
     */
    @Test
    public void getItemByConstDIreturnMusicCD() {
        Store store = context.getBean("storeConstructorDI", Store.class);
        assertEquals(store.getSell()
            .getType(), "MusicCD");
    }

}
