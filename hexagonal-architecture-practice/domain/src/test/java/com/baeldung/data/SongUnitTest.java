package com.baeldung.data;

import com.baeldung.exceptions.DomainException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class SongUnitTest {

    @Test
    public void testDomainExceptionInObjectCreationUnitTest() {
        assertThrows(DomainException.class, () -> new Song("title", "author", 0L));
    }

    @Test
    public void testObjectCreationSuccessUnitTest() {
        Song song = new Song("title", "author", 300L);
        assertNotNull(song);
    }

}