package com.baeuldung.jsdc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeuldung.jsdc.copy.DeepCopier;
import com.baeuldung.jsdc.copy.ShallowCopier;
import com.baeuldung.jsdc.model.Book;

public class ShallowAndDeepCopyTest {
    Book originalBook;
    DeepCopier deepCopier;
    ShallowCopier shallowCopier;

    @Before
    public void init() {
        originalBook = new Book();
        originalBook.setAuthor("M.C.Y");
        originalBook.setTitle("Mysteries of Castle Catia");

        deepCopier = new DeepCopier();
        shallowCopier = new ShallowCopier();
    }

    @Test
    void whenDeepCopiedThenOriginalInstanceIsNotEffectedFromCopiedInstance() {
        Book originalBook = new Book("Mysteries of Castle Catia", "M.C.Y.");
        Book deepCopy = deepCopier.copy(originalBook);
        deepCopy.setTitle("Mysteries of Fort Catia");
        deepCopy.setAuthor("G.C.Y.");

        assertNotEquals(deepCopy.getAuthor(), originalBook.getAuthor());
        assertNotEquals(deepCopy.getTitle(), originalBook.getTitle());
    }

    @Test
    void whenShallowCopiedThenOriginalInstanceIsEffectedFromCopiedInstance() {
        Book originalBook = new Book("Mysteries of Castle Catia", "M.C.Y.");
        Book shallowCopy = shallowCopier.copy(originalBook);
        shallowCopy.setTitle("Mysteries of Fort Catia");
        shallowCopy.setAuthor("G.C.Y.");

        assertEquals(shallowCopy.getAuthor(), originalBook.getAuthor());
        assertEquals(shallowCopy.getTitle(), originalBook.getTitle());
    }
}
