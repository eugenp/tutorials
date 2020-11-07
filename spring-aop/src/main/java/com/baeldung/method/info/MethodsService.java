package com.baeldung.method.info;

import org.springframework.stereotype.Component;

@Component
public class MethodsService {

  @MethodsInformation(methodsEnum = Methods.FOO_METHOD)
  public void fooMethod(MethodArgs firstMethodArgs, MethodArgs secondMethodArgs) throws Exception {

  }

  @MethodsInformation(methodsEnum = Methods.BAR_METHOD)
  public void barMethod(MethodArgs firstMethodArgs, MethodArgs secondMethodArgs) throws Exception {

  }
}
