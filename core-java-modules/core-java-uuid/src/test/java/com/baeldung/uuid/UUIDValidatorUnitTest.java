package com.baeldung.uuid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDValidatorUnitTest {

  @Test
  public void whenValidUUIDStringIsValidated_thenValidationSucceeds() {
    String validUUID = "26929514-237c-11ed-861d-0242ac120002";
    Assertions.assertEquals(UUID.fromString(validUUID).toString(), validUUID);

    String invalidUUID = "invalid-uuid";
    Assertions.assertThrows(IllegalArgumentException.class, () -> UUID.fromString(invalidUUID));
  }

  @Test
  public void whenUUIDIsValidatedUsingRegex_thenValidationSucceeds() {
    Pattern UUID_REGEX =
        Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    Assertions.assertTrue(UUID_REGEX.matcher("26929514-237c-11ed-861d-0242ac120002").matches());

    Assertions.assertFalse(UUID_REGEX.matcher("invalid-uuid").matches());
  }
}
