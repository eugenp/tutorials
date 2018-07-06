package com.baeldung.mavenjavakotlin;

import com.baeldung.mavenjavakotlin.services.JavaService;

public class Application {

  private static final String JAVA = "java";

  public static void main(String[] args) {
    String language = args[0];
    switch (language) {
      case JAVA:
        new JavaService().sayHello();
        break;
      default:
        // Do nothing
        break;
    }
  }

}
