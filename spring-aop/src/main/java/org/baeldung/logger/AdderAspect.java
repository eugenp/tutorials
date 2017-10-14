package org.baeldung.logger;

import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdderAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String POINTCUT = "execution(* org.baeldung.logger.SampleAdder+.*(..))";

    @Around(POINTCUT)
    public Object time(final ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Arguments passed to method are: " + Arrays.toString(joinPoint.getArgs()));
        final Object result = joinPoint.proceed();
        logger.info("Result from method is: " + result);
        return result;
    }
}
