package com.baeldung.classmethodadvice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingInterceptor {
  private static final Log LOG = LogFactory.getLog(TracingInterceptor.class);

  @Around("@within(Trace)")
  public Object traceLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    LOG.trace("Entering " + proceedingJoinPoint.getSignature());
    Object returnValue = proceedingJoinPoint.proceed();
    LOG.trace("Exiting " + proceedingJoinPoint.getSignature());

    return returnValue;
  }
}
