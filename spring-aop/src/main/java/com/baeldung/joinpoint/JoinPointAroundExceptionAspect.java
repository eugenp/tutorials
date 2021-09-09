package com.baeldung.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class JoinPointAroundExceptionAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinPointAroundExceptionAspect.class);

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @Around("articleListPointcut()")
    public Object aroundAdviceException(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed(pjp.getArgs());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            log.info("Retrying operation");
            return pjp.proceed(pjp.getArgs());
        }
    }
}
