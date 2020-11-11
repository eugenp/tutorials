package com.baeldung.method.info;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MethodsServiceIntegrationTest {

  @Autowired
  MethodsService methodsService;

  MethodArgs firstMethodArgs;
  MethodArgs secondMethodArgs;

  @BeforeEach
  public void setup() {
    firstMethodArgs = new MethodArgs();
    firstMethodArgs.setFooArg("first arg foo");
    firstMethodArgs.setBarArg("first arg bar");

    secondMethodArgs = new MethodArgs();
    secondMethodArgs.setFooArg("second arg foo");
    secondMethodArgs.setBarArg("second arg bar");
  }

  @Test
  void fooMethod() throws Exception {
    methodsService.fooMethod(firstMethodArgs, secondMethodArgs);
  }

  @Test
  void barMethod() throws Exception {
    methodsService.barMethod(firstMethodArgs, secondMethodArgs);
  }
}
