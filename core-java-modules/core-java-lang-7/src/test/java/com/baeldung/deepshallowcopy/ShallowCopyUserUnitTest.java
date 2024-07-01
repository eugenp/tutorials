package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ShallowCopyUserUnitTest {

    @Test
    public void whenCloningUsingShallowCopy_thenObjectsShouldNotBeSameButFieldsShouldBe() {
        ShallowCopyUser originalUser = new ShallowCopyUser("Jack Whiteman", Arrays.asList("Admin", "User"));
        ShallowCopyUser clonedUser = null;
        try {
            clonedUser = (ShallowCopyUser) originalUser.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        assertNotSame(originalUser, clonedUser, "Cloned user should be a different object.");
        assertEquals(originalUser.getRoles(), clonedUser.getRoles(), "Roles should be the same list (shallow copy).");
        clonedUser.getRoles()
            .add("Guest");
        assertEquals(originalUser.getRoles(), clonedUser.getRoles(), "Changes to the roles list should affect both users.");
    }
}