package com.baeldung.method.info;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@Aspect
@Component
public class MethodsAspect {

  @Before(value="@annotation(com.baeldung.method.info.MethodsInformation)")
  public void getMethodInfo(JoinPoint joinPoint) {

    //Method Information
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();

    System.out.println("full method description: " + signature.getMethod());

    System.out.println("method name: " + signature.getMethod().getName());

    System.out.println("declaring type: " + signature.getDeclaringType());

    //Method args
    System.out.println("Method args names:");
    Arrays.stream(signature.getParameterNames()).forEach(s -> System.out.println("arg name: " + s));

    System.out.println("Method args types:");
    Arrays.stream(signature.getParameterTypes()).forEach(s -> System.out.println("arg type: " + s));

    System.out.println("Method args values:");
    Arrays.stream(joinPoint.getArgs()).forEach(o -> System.out.println("arg value: " + o.toString()));

    //Additional Information
    System.out.println("returning type: " + signature.getReturnType());
    System.out.println("method modifier: " + Modifier.toString(signature.getModifiers()));
    Arrays.stream(signature.getExceptionTypes())
        .forEach(aClass -> System.out.println("exception type: " + aClass));

    //Method annotation
    Method method = signature.getMethod();
    MethodsInformation methodsInformation = method.getAnnotation(MethodsInformation.class);
    System.out.println("Method annotation: " + methodsInformation.methodsEnum());
    System.out.println("Method annotation description: " + methodsInformation.methodsEnum().getDescription());

  }
}

