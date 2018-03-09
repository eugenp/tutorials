// <start id="magician_java" />
package com.springinaction.springidol;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AlternateMagician implements MindReader {
  private String thoughts;

  @Pointcut("execution(* com.springinaction.springidol."
      + "Thinker.thinkOfSomething(String))")
  public void thinking() {
  }

  @Before("thinking() && args(thoughts)")
  public void interceptThoughts(String thoughts) {
    System.out.println("Intercepting volunteer's thoughts : " + thoughts);
    this.thoughts = thoughts;
  }

  public String getThoughts() {
    return thoughts;
  }
}
// <end id="magician_java" />
