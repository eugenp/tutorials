package com.baeldung.joinpoint;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class JoinPointAspect {

    private static final Logger log = LoggerFactory.getLogger(JoinPointAspect.class);

    private final static Map<Object, String> CACHE = new HashMap<>();

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

    @AfterThrowing("articleListPointcut()")
    public void logExceptions(JoinPoint jp, Exception e) {
        log.error(e.getMessage(), e);
    }

    @Around("articleListPointcut()")
    public Object aroundAdviceCache(ProceedingJoinPoint pjp) throws Throwable {
        Object articles = CACHE.get(pjp.getArgs());
        if (articles == null) {
            articles = pjp.proceed(pjp.getArgs());
        }
        return articles;
    }

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
