package com.baeldung.deepshallowcopy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class DeepCopyUserUnitTest {

    @Test
    public void whenCloningUsingDeepCopy_thenObjectsShouldNotShareReferences() {
        DeepCopyUser originalUser = new DeepCopyUser("Jack Madson", Arrays.asList("Admin", "User"));
        DeepCopyUser copiedUser = new DeepCopyUser(originalUser);

        assertNotSame(originalUser, copiedUser, "Copied user should be a different object.");
        assertNotSame(originalUser.getRoles(), copiedUser.getRoles(), "Roles list should be a different object.");
        assertEquals(originalUser.getRoles(), copiedUser.getRoles(), "Roles should be the same content.");
        copiedUser.getRoles()
            .add("Guest");
        assertEquals(2, originalUser.getRoles()
            .size(), "Original user roles should not be affected by changes to the copied user.");
        assertEquals(3, copiedUser.getRoles()
            .size(), "Copied user roles should reflect the new addition.");
    }
}