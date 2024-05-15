package com.baeldung.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SecuredMethodAspect {
    private static final Logger logger = LoggerFactory.getLogger(SecuredMethodAspect.class);

    @Pointcut("@annotation(secured)")
    public void callAt(Secured secured) {
    }

    @Around("callAt(secured)")
    public Object around(ProceedingJoinPoint pjp, Secured secured) throws Throwable {
        if (secured.isLocked()) {
            logger.info(pjp.getSignature().toLongString() + " is locked");
            return null;
        } else {
            return pjp.proceed();
        }
    }
}
