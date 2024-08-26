package com.baeldung.clobtostringandviceversa;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobToStringAndViceVersa {
     public void convertLargeCLOBtoString() throws SQLException, IOException {
          Clob clob = new javax.sql.rowset.serial.SerialClob("".toCharArray()); // Assume we have a large clob size > Integer.MAX_VALUE
          try (Reader reader = clob.getCharacterStream()) {
              char[] buffer = new char[4096];
              int charsRead;
              while ((charsRead = reader.read(buffer)) != -1) {
                // processChunk(buffer, charsRead); //Process each chunk as needed
              }
          }
     }
}
