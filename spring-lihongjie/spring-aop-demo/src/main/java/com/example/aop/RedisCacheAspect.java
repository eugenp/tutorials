package com.example.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Redis缓存切面
 * Created by ts495 on 2017/9/9.
 */
@Aspect
@Component
public class RedisCacheAspect {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisTemplate redisTemplate;

    @Pointcut("execution(* com.yupaits.docs.repository..*.find*(..))")
    public void setCache() {}

    @Pointcut("execution(* com.yupaits.docs.repository..*.save(..)) " +
            "|| execution(* com.yupaits.docs.repository..*.delete(..))" +
            "|| execution(* com.yupaits.docs.repository..*.update*(..))")
    public void evictCache() {}

    @Around(value = "setCache()")
    public Object aroundSetCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();

        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        String key = generateKey(className, methodName, args);
        Object value = redisTemplate.opsForHash().get(className, key);
        Object result = null;
        if (null == value) {
            result = joinPoint.proceed();
            redisTemplate.opsForHash().put(className, key, result);
            logger.debug("SPEND TIME: {}ms, FROM DB, CACHE_KEY: {}", (System.currentTimeMillis() - startTime), key);
        } else {
            result = value;
            logger.debug("SPEND TIME: {}ms, FROM CACHE_NAME: {}, CACHE_KEY: {}", (System.currentTimeMillis() - startTime), className, key);
        }
        return result;
    }

    @Around(value = "evictCache()")
    public Object aroundEvictCache(ProceedingJoinPoint joinPoint) throws Throwable {
        Long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        redisTemplate.delete(className);
        logger.info("SPEND TIME: {}ms, DELETE CACHE_NAME: {}", (System.currentTimeMillis() - startTime), className);
        return result;
    }

    /**
     * 生成缓存Key值，className作为缓存name
     * @param className 类名
     * @param methodName 方法名
     * @param args 参数
     * @return Key
     */
    private String generateKey(String className, String methodName, Object[] args) {
        String delimiter = "|";
        StringBuilder stringBuilder = new StringBuilder(className).append(delimiter).append(methodName);
        for (Object arg : args) {
            stringBuilder.append(delimiter).append(arg);
        }
        return stringBuilder.toString();
    }
}
