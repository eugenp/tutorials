package com.baeldung.joinpoint;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@DeclarePrecedence("JoinPointAroundCacheAspect, JoinPointAroundExceptionAspect")
public class JoinPointAfterThrowingAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinPointAfterThrowingAspect.class);

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @AfterThrowing(
      pointcut = "articleListPointcut()",
      throwing = "e"
    )
    public void logExceptions(JoinPoint jp, Exception e) {
        log.error(e.getMessage(), e);
    }
}
