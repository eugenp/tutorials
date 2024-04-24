package com.baeldung.exception.eof;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.io.EOFException;

public class EOFExceptionDemo2 {
    public static void readInput() throws Exception {
      InputStream is = new ByteArrayInputStream("123".getBytes());
      DataInputStream in = new DataInputStream(is);
      while (true) {
        try {
          char value = (char)in.readByte();
          System.out.println("Input value: " + value);
        } catch (EOFException e) {
          System.out.println("End of file");
          break;
        }
      }      
    }
}