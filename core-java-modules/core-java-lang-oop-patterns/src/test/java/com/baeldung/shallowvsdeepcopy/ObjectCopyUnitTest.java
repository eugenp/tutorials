package com.baeldung.shallowvsdeepcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class ObjectCopyUnitTest {

    @Test
    public void whenCopyingTheObjectShallowly_thenChangesInTheOriginalObjectShouldBeMirrored() {
        App app = new App("user1");
        Phone phone = new Phone(app);

        Phone copiedPhone = phone.shallowCopy();
        app.setLoggedInUser("user2");

        assertEquals("user2", copiedPhone.getApp().getLoggedInUser());
    }

    @Test
    public void whenCopyingTheObjectDeeply_thenChangesInTheOriginalObjectShouldNotBeMirrored() {
        App app = new App("user1");
        Phone phone = new Phone(app);

        Phone copiedPhone = phone.deepCopy();
        app.setLoggedInUser("user2");

        assertEquals("user1", copiedPhone.getApp().getLoggedInUser());
    }
}
