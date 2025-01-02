package com.baeldung.clobtostringandviceversa;

import org.junit.Test;

import java.sql.Clob;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ClobToStringAndViceVersaUnitTest {
    @Test
    public void givenCLOB_whenSizeLessThanMaxValue_thenConvertToString() throws SQLException {
        Clob clob = new javax.sql.rowset.serial.SerialClob("This is a sample CLOB content.".toCharArray());
        long clobLength = clob.length();
        if (clobLength <= Integer.MAX_VALUE) {
            String clobAsString = clob.getSubString(1, (int) clobLength);
            assertEquals("This is a sample CLOB content.", clobAsString);
        }
    }

    @Test
    public void givenString_whenConvertToCLOB_thenVerifyCorrectness() throws SQLException {
        String sampleText = "This is a sample text to be stored as CLOB.";

        char[] charArray = sampleText.toCharArray();

        Clob clob = new javax.sql.rowset.serial.SerialClob(charArray);

        assertEquals(sampleText, clob.getSubString(1, (int) clob.length()));
    }
}
