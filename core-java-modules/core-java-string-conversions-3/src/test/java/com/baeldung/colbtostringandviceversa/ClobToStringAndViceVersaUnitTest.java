package com.baeldung.colbtostringandviceversa;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.sql.Clob;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class ClobToStringAndViceVersaUnitTest {
    @Test
    public void givenCLOB_whenConvertToString_thenVerifyCorrectness() throws SQLException, IOException {
        Clob clob = new javax.sql.rowset.serial.SerialClob("This is a sample CLOB content.".toCharArray());

        String clobAsString;
        try (Reader reader = clob.getCharacterStream();
             StringWriter w = new StringWriter()) {
            char[] buffer = new char[4096];
            int charsRead;
            while ((charsRead = reader.read(buffer)) != -1) {
                w.write(buffer, 0, charsRead);
            }
            clobAsString = w.toString();
        }

        assertEquals("This is a sample CLOB content.", clobAsString);
    }

    @Test
    public void givenString_whenConvertToCLOB_thenVerifyCorrectness() throws SQLException {
        String sampleText = "This is a sample text to be stored as CLOB.";

        char[] charArray = sampleText.toCharArray();

        Clob clob = new javax.sql.rowset.serial.SerialClob(charArray);

        assertEquals(sampleText, clob.getSubString(1, (int) clob.length()));
    }
}
