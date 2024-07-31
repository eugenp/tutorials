package com.baeldung.hexformat;

import java.util.HexFormat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UppercaseLowercaseOutputUnitTest {

    @Test
    public void givenInitialisedHexFormat_whenByteArrayIsPassed_thenLowerCaseHexStringRepresentationIsReturned() {
        HexFormat hexFormat = HexFormat.of();
        String bytesAsString = hexFormat.formatHex(new byte[] { -85, -51, -17, 1, 35, 69, 103, -119});
        assertTrue(isLowerCase(bytesAsString));
    }

    @Test
    public void givenInitialisedHexFormatWithUpperCaseOption_whenByteArrayIsPassed_thenLowerCaseHexStringRepresentationIsReturned() {
        HexFormat hexFormat = HexFormat.of().withUpperCase();
        String bytesAsString = hexFormat.formatHex(new byte[] { -85, -51, -17, 1, 35, 69, 103, -119});
        assertTrue(isUpperCase(bytesAsString));
    }

    private boolean isLowerCase(String str) {
        char[] charArray = str.toCharArray();
        for (int i=0; i < charArray.length; i++) {
            if (Character.isUpperCase(charArray[i]))
                return false;
        }
        return true;
    }

    private boolean isUpperCase(String str) {
        char[] charArray = str.toCharArray();
        for (int i=0; i < charArray.length; i++) {
            if (Character.isLowerCase(charArray[i]))
                return false;
        }
        return true;
    }
}
