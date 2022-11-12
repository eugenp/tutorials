package com.baeldung.deepandshallowcopy;

import org.junit.Assert;
import org.junit.Test;

public class DeepShallowCopyBookUnitTest {

    @Test
    public void testDeepCopyWithManualCopy() {
        Isbn isbn = new Isbn("02928383KHYBER");
        Book theHobbit = new Book("The Hobbit", "Tolkien", isbn);
        Book copyOfHobbit = new Book();

        // Copying the object by manually copying all the data

        copyOfHobbit.setTitle(theHobbit.getTitle());
        copyOfHobbit.setAuthor(theHobbit.getAuthor());
        copyOfHobbit.setIsbn(theHobbit.getIsbn());

        Assert.assertNotEquals(copyOfHobbit, theHobbit);
        Assert.assertEquals(copyOfHobbit.getIsbn(), theHobbit.getIsbn());

        theHobbit.setIsbn(new Isbn("10270863ILO"));
        Assert.assertNotEquals(theHobbit.getIsbn(), copyOfHobbit.getIsbn());
    }

    @Test
    public void testDeepCopyUsingClone() {
        Isbn isbn = new Isbn("02928383KHYBER");
        Book theHobbit = new Book("The Hobbit", "Tolkien", isbn);


        // Copying the object by cloning it
        
        Book copyOfHobbit = (Book) theHobbit.clone();

        Assert.assertNotEquals(copyOfHobbit, theHobbit);

        theHobbit.setIsbn(new Isbn("10270863ILO"));
        Assert.assertNotEquals(theHobbit.getIsbn(), copyOfHobbit.getIsbn());
    }
}