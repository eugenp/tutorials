package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.pojo.shallowcopy.BookDetailShallowCopy;
import com.baeldung.pojo.shallowcopy.BookShallowCopy;

public class ShallowCopyUnitTest {

    @Test
    public void whenCloneChanges_thenOriginalObjectAlsoChanges() throws CloneNotSupportedException {

        BookDetailShallowCopy bookDetail = new BookDetailShallowCopy(2021);
        BookShallowCopy originalObj = new BookShallowCopy("Head First SQL", bookDetail);

        BookShallowCopy shallowCopyObj = (BookShallowCopy) originalObj.clone();

        shallowCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertEquals(originalObj, shallowCopyObj);
    }

    @Test
    public void whenCopyChanges_thenOriginalObjectAlsoChanges() {

        BookDetailShallowCopy bookDetail = new BookDetailShallowCopy(2021);
        BookShallowCopy originalObj = new BookShallowCopy("Head First SQL", bookDetail);

        BookShallowCopy shallowCopyObj = new BookShallowCopy(originalObj);

        shallowCopyObj.getBookDetail()
            .setYearOfPublication(2022);

        assertEquals(shallowCopyObj, originalObj);

    }

}
