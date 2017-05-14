package com.baeldung.ditypes;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = TestConfig.class)
public class SetterTypeBookManagerTest {

    @Autowired
    SetterTypeBookManager bookManager;

    @Test
    public void create() {
        Boolean result = bookManager.create(new Book("Title", "1234SDK"));
        assertTrue(result);
    }

    @Test
    public void read() {
        Book book = bookManager.read("1234SDK");
        assertNotNull(book);
        assertEquals("Title", book.title);
        assertEquals("1234SDK", book.isbn);
    }

    @Test
    public void update() {
        Boolean result = bookManager.update("1234SDK", new Book("Title 2", "1234SDK"));
        assertTrue(result);
    }

    @Test
    public void delete() {
        Boolean result = bookManager.delete("1234SDK");
        assertTrue(result);
    }
}
