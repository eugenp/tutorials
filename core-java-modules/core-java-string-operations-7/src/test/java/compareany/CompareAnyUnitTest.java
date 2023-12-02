package compareany;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

public class CompareAnyUnitTest {

    @Test
    void givenStrings_whenCompareUsingIf_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(matchAnyWithIf(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(matchAnyWithIf(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingArrayUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingArrayUtils(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(compareWithAnyUsingArrayUtils(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingStringUtils_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingStringUtils(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(compareWithAnyUsingStringUtils(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingStream_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingStream(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(compareWithAnyUsingStream(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingSet_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingSet(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(compareWithAnyUsingSet(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    @Test
    void givenStrings_whenCompareUsingRegularExpression_thenSuccess() {
        String presentString = "Apple";
        String notPresentString = "Avocado";

        assertTrue(compareWithAnyUsingRegularExpression(presentString, "Mango", "Papaya", "PineApple", "Apple"));
        assertFalse(compareWithAnyUsingRegularExpression(notPresentString, "Mango", "Papaya", "PineApple", "Apple"));
    }

    private boolean matchAnyWithIf(String str, String ... strs) {
        for(String s : strs) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareWithAnyUsingStringUtils(String str, String ... strs) {
        return StringUtils.containsAny(str, strs);
    }

    private boolean compareWithAnyUsingSet(String str, String ... strs) {
        return Set.of(strs).contains(str);
    }

    private static boolean compareWithAnyUsingRegularExpression(String str, String ... strs) {
        return str.matches(String.join("|", strs));
    }

    private static boolean compareWithAnyUsingStream(String str, String ... strs) {
        return Arrays.stream(strs).anyMatch(str::equals);
    }

    private static boolean compareWithAnyUsingArrayUtils(String str, String ... strs) {
        return ArrayUtils.contains(strs, str);
    }

}
