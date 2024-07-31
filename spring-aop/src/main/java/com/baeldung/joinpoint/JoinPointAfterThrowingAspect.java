package com.baeldung.joinpoint;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class JoinPointAfterThrowingAspect {

    private static final java.util.logging.Logger log = Logger.getLogger(JoinPointAfterThrowingAspect.class.getName());

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @AfterThrowing(
      pointcut = "articleListPointcut()",
      throwing = "e"
    )
    public void logExceptions(JoinPoint jp, Exception e) {
        log.severe(e.getMessage());
    }
}
