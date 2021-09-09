package com.baeldung.joinpoint;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@DeclarePrecedence("JoinPointAfterThrowingAspect, JoinPointAroundCacheAspect, JoinPointAroundExceptionAspect")
public class JoinPointBeforeAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinPointBeforeAspect.class);

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @Before("articleListPointcut()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info(
          "Method {} executed with {} arguments",
          joinPoint.getStaticPart().getSignature(),
          joinPoint.getArgs()
        );
    }
}
