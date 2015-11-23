package org.baeldung.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
@Aspect
public class CachingAspect {

    @Autowired
    SimpleCache cache;

    @Pointcut(value = "@target(org.springframework.stereotype.Repository) && execution(* *..find*(Long))")
    public void repositoryMethods() {};

    @AfterReturning(value = "repositoryMethods()", returning = "retVal")
    public void logMethodCall(JoinPoint jp, Object retVal) throws Throwable {
        Long id = (Long) jp.getArgs()[0];
        cache.put(id, retVal);
    }
}
