package com.baeldung.exception.eof;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

public class EOFExceptionDemo {
    public static void readInput() throws Exception {
      InputStream is = new ByteArrayInputStream("123".getBytes());
      DataInputStream in = new DataInputStream(is);
      while (true) {
        char value = (char)in.readByte();
        System.out.println("Input value: " + value);
      }
    }
}