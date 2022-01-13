package com.baeldung.dddhexagonalcore;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baeldung.dddhexagonalcore.inside.BookApplication;
import com.baeldung.dddhexagonalcore.inside.IBookStoragePort;


class BookApplicationUnitTest {

    private Map<String, String> realStorage;
    private IBookStoragePort storage;
    private BookApplication tested; 
    
    @BeforeEach
    void setUp() {
        
        realStorage = new HashMap<>();
        realStorage.put("0123456789", "Book 1");
        realStorage.put("9876543210", "Book 2");
        
        storage = mock(IBookStoragePort.class);
        when(storage.getAllBooks()).thenReturn(realStorage);
        when(storage.getBookTitle(anyString())).thenAnswer(new Answer<String>() {

            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                
                return realStorage.get(invocation.getArgument(0));
            }
        });
        
        tested = new BookApplication(storage);
    }
    

    @Test
    void testCheckIsbn() {
        
//      Length 10 or 13 are valid
        assertDoesNotThrow(() -> tested.checkIsbn("0123456789"));
        assertDoesNotThrow(() -> tested.checkIsbn("0123456789123"));
        
//      Other lengths are invalid
        assertThrows(IllegalArgumentException.class, () -> tested.checkIsbn("012345678"));
        assertThrows(IllegalArgumentException.class, () -> tested.checkIsbn("01234567890"));
    }

    @Test
    void testStoreBookToDatabase() {
        
        assertThrows(IllegalArgumentException.class, () -> tested.storeBookToDatabase("012345678", "invalid isbn"));
        assertDoesNotThrow(() -> tested.storeBookToDatabase("0123456789", "valid isbn"));
    }

    @Test
    void testGetBookFromDatabase() {
        
        assertNull(tested.getBookFromDatabase("1234567890"));
        assertEquals("Book 1", tested.getBookFromDatabase("0123456789"));
        assertEquals("Book 2", tested.getBookFromDatabase("9876543210"));
    }

    @Test
    void testGetAllBooksFromDatabase() {
                
        assertTrue(tested.getAllBooksFromDatabase().size() == 2);
        assertEquals("Book 1", tested.getAllBooksFromDatabase().get("0123456789"));
        assertEquals("Book 2", tested.getAllBooksFromDatabase().get("9876543210"));
        assertNull(tested.getAllBooksFromDatabase().get("987654321"));
    }

}
