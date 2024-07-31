package com.baeldung.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class JoinPointAroundExceptionAspect {

    private static final java.util.logging.Logger log = Logger.getLogger(JoinPointAroundExceptionAspect.class.getName());

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @Around("articleListPointcut()")
    public Object aroundAdviceException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            log.severe(e.getMessage());
            log.info("Retrying operation");
            return pjp.proceed(pjp.getArgs());
        }
    }
}
