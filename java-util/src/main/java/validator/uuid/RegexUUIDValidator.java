package validator.uuid;

import java.util.regex.Pattern;

public class RegexUUIDValidator {

  private final static Pattern UUID_REGEX =
      Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

  public static boolean isUUID(String string) {
    if (string == null) {
      return false;
    }
    return UUID_REGEX.matcher(string).matches();
  }
}
