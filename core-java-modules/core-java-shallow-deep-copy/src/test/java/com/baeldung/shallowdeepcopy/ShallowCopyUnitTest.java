package com.baeldung.shallowdeepcopy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.baeldung.pojo.shallowcopy.BookDetailShallowCopy;
import com.baeldung.pojo.shallowcopy.BookShelfInfoShallowCopy;

public class ShallowCopyUnitTest {

    @Test
    public void whenCreatingShallowCopyWithCloneable_andChangeCloneDate_thenOriginalObjectAlsoChanges() throws CloneNotSupportedException {

        BookDetailShallowCopy bookDetail = new BookDetailShallowCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoShallowCopy originalObj = new BookShelfInfoShallowCopy(1111, "Head First SQL", "A12", bookDetail);
        // making a shallow copy with clone.
        // Only references are copied of the objects having COMPOSITION RELATIONSHIP with this class.
        // Hence change in one reference shows change in the other too as they point to same memory location.
        BookShelfInfoShallowCopy shallowCopy = (BookShelfInfoShallowCopy) originalObj.clone();
        // making a change in year of the copied object shows up in original object too
        shallowCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertEquals(originalObj, shallowCopy);
    }

    @Test
    public void whenCreatingShallowCopyWithCopyConstructor_andChangeCopiedDate_thenOriginalObjectAlsoChanges() {

        BookDetailShallowCopy bookDetail = new BookDetailShallowCopy("Lynn Beighley", "SHROFF", 2021, 2);
        BookShelfInfoShallowCopy originalObj = new BookShelfInfoShallowCopy(1111, "Head First SQL", "A12", bookDetail);
        // create a shallow copy through copy constructor,
        // only the references having COMPOSITION RELATIONSHIP with this class are copied.
        // actual values are not copied.
        BookShelfInfoShallowCopy shallowCopy = new BookShelfInfoShallowCopy(originalObj);// triggers shallow copy
        // change the copy and we see change in the original object too.
        shallowCopy.getBookDetail()
            .setYearOfPublication(2022);

        assertEquals(shallowCopy, originalObj);

    }

}
