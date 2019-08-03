import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringEqualsIgnoreCaseUnitTest {

    @Test
    public void givenEqualStringsWithDifferentCase_whenUsingEqualsIgnoreCase_ThenTheyAreEqual() {
        String string1 = "equals ignore case";
        String string2 = "EQUALS IGNORE CASE";

        assertThat(string1.equalsIgnoreCase(string2)).isTrue();
    }
}
