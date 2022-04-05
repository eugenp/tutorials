package com.baeldung.undeclared;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UndeclaredAspect {

    @Around("@annotation(undeclared)")
    public Object advise(ProceedingJoinPoint pjp, ThrowUndeclared undeclared) throws Throwable {
        throw new SomeCheckedException("AOP Checked Exception");
    }
}
