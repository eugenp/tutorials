package com.baeldung.optionaluses;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsesForOptionalUnitTest {

    @Test
    public void givenNonExistentUserId_whenSearchForUser_andNoNullCheck_thenThrowException() {

        UserRepositoryWithNull userRepositoryWithNull = new UserRepositoryWithNull();
        String nonExistentUserId = "4";

        assertThrows(NullPointerException.class, () -> {
            System.out.println("User name: " + userRepositoryWithNull.findById(nonExistentUserId)
              .getName());
        });
    }

    @Test
    public void givenNonExistentUserId_whenSearchForUser_thenOptionalShouldBeTreatedProperly() {

        UserRepositoryWithOptional userRepositoryWithOptional = new UserRepositoryWithOptional();
        String nonExistentUserId = "4";

        String userName = userRepositoryWithOptional.findById(nonExistentUserId)
          .orElse(new User("0", "admin"))
          .getName();

        assertEquals("admin", userName);
    }

    @Test
    public void givenExistentUserId_whenFoundUserWithNameStartingWithMInRepositoryUsingNull_thenNameShouldBeUpperCased() {

        UserRepositoryWithNull userRepositoryWithNull = new UserRepositoryWithNull();

        User user = userRepositoryWithNull.findById("2");
        String upperCasedName = "";

        if (user != null) {
            if (user.getName().startsWith("M")) {
                upperCasedName = user.getName().toUpperCase();
            }
        }

        assertEquals("MARIA", upperCasedName);
    }

    @Test
    public void givenExistentUserId_whenFoundUserWithNameStartingWithMInRepositoryUsingOptional_thenNameShouldBeUpperCased() {

        UserRepositoryWithOptional userRepositoryWithOptional = new UserRepositoryWithOptional();

        String upperCasedName = userRepositoryWithOptional.findById("2")
          .filter(u -> u.getName().startsWith("M"))
          .map(u -> u.getName().toUpperCase())
          .orElse("");

        assertEquals("MARIA", upperCasedName);
    }
}
