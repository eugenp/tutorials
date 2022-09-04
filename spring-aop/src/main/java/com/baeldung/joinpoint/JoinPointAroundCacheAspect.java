package com.baeldung.joinpoint;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class JoinPointAroundCacheAspect {

    public final static Map<Object, Object> cache = new HashMap<>();

    @Pointcut("execution(* com.baeldung.joinpoint.ArticleService.getArticleList(..))")
    public void articleListPointcut() {
        // pointcut signatures
    }

    @Around("articleListPointcut()")
    public Object aroundAdviceCache(ProceedingJoinPoint pjp) throws Throwable {
        Object articles = cache.get(pjp.getArgs());
        if (articles == null) {
            articles = pjp.proceed(pjp.getArgs());
            cache.put(pjp.getArgs(), articles);
        }
        return articles;
    }
}
