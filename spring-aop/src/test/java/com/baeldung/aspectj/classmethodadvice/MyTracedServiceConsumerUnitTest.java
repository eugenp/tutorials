package com.baeldung.aspectj.classmethodadvice;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class MyTracedServiceConsumerUnitTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Spy
  private MyTracedService myTracedService;

  @Test
  public void whenCallingConsumer_thenServiceIsCalled() {
    doNothing().when(myTracedService).performSomeLogic();
    doNothing().when(myTracedService).performSomeAdditionalLogic();

    new MyTracedServiceConsumer(myTracedService);

    verify(myTracedService).performSomeLogic();
    verify(myTracedService).performSomeAdditionalLogic();
  }
}