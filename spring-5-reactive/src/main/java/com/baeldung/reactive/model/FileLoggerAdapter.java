package com.baeldung.reactive.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLoggerAdapter implements Logger {

  @Override
  public void show(Message message) {

    File tmpFile = null;
    try {
      System.out.println("Called FileLogger");
      tmpFile = File.createTempFile("test","tmp");
      FileWriter fileWriter = new FileWriter(tmpFile);
      fileWriter.write(message.getMessage());
    } catch (IOException e) {
      e.printStackTrace();
    }


  }
}
