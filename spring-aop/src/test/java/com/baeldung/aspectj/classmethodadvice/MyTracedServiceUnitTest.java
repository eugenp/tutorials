package com.baeldung.aspectj.classmethodadvice;

import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.system.OutputCaptureRule;

import static org.junit.Assert.assertTrue;

/*
 * When running this test class, the tests may fail unless you build the code with Maven first. You
 * must ensure the AspectJ compiler executes to weave in the Aspect's logic. Without the Aspect
 * weaved into the class under test, the trace logging will not be written to stdout.
 */
public class MyTracedServiceUnitTest {

  @Rule
  public OutputCaptureRule outputCaptureRule = new OutputCaptureRule();

  @Test
  public void whenPerformingSomeLogic_thenTraceAndInfoOutputIsWritten() {
    MyTracedService myTracedService = new MyTracedService();
    myTracedService.performSomeLogic();

    String output = outputCaptureRule.getOut();
    assertTrue(output.contains("TracingAspect - Entering MyTracedService.performSomeLogic"));
    assertTrue(output.contains("MyTracedService - Inside performSomeLogic"));
    assertTrue(output.contains("TracingAspect - Exiting MyTracedService.performSomeLogic"));
  }

  @Test
  public void whenPerformingSomeAdditionalLogic_thenTraceAndInfoOutputIsWritten() {
    MyTracedService myTracedService = new MyTracedService();
    myTracedService.performSomeAdditionalLogic();

    String output = outputCaptureRule.getOut();
    assertTrue(output.contains("TracingAspect - Entering MyTracedService.performSomeAdditionalLogic"));
    assertTrue(output.contains("MyTracedService - Inside performSomeAdditionalLogic"));
    assertTrue(output.contains("TracingAspect - Exiting MyTracedService.performSomeAdditionalLogic"));
  }
}