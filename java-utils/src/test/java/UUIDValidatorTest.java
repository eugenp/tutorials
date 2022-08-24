import com.baeldung.RegexUUIDValidator;
import com.baeldung.UUIDCustomValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UUIDValidatorTest {

  @Test
  public void givenAValidUUIDString_whenValidatingIt_thenValidationSucceeds() {
    Assertions.assertTrue(UUIDCustomValidator
        .isUUID("26929514-237c-11ed-861d-0242ac120002"));
    Assertions.assertTrue(RegexUUIDValidator.isUUID("26929514-237c-11ed-861d-0242ac120002"));
  }
}
