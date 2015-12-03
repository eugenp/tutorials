package org.baeldung.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Component
@Aspect
public class LoggingAspect {

    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    private ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("[yyyy-mm-dd hh:mm:ss:SSS]");
        }
    };

    @Pointcut("@target(org.springframework.stereotype.Repository)")
    public void repositoryMethods() {}

    @Before("repositoryMethods()")
    public void logMethodCall(JoinPoint jp) throws Throwable {
        String methodName = jp.getSignature().getName();
        logger.info(sdf.get().format(new Date()) + methodName);
    }
}
