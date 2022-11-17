package com.baeldung.optionaluses;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

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

        assertEquals(userName, "admin");
    }
}
