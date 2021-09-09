package com.baeldung.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
@DeclarePrecedence("JoinPointAroundExceptionAspect")
public class JoinPointAroundCacheAspect {

    private final static Map<Object, String> CACHE = new HashMap<>();

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() { }

    @Around("articleListPointcut()")
    public Object aroundAdviceCache(ProceedingJoinPoint pjp) throws Throwable {
        Object articles = CACHE.get(pjp.getArgs());
        if (articles == null) {
            articles = pjp.proceed(pjp.getArgs());
        }
        return articles;
    }
}
