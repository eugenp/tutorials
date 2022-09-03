package validator.uuid;

import java.util.UUID;

public class UUIDCustomValidator {

  public static boolean isUUID(String stringToBeChecked) {
    try {
      return (UUID.fromString(stringToBeChecked).toString().equals(stringToBeChecked));
    } catch (IllegalArgumentException exception) {
    }
    return false;
  }
}
