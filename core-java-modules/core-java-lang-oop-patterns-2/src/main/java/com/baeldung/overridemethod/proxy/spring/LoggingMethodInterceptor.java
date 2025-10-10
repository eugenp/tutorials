package com.baeldung.overridemethod.proxy.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingMethodInterceptor implements MethodInterceptor {
    private static final Logger log = LoggerFactory.getLogger(LoggingMethodInterceptor.class);
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        
        log.debug("SPRING PROXY: Intercepting method: {}", invocation.getMethod().getName());
        
        Object result = invocation.proceed();
        
        log.debug("SPRING PROXY: Method {} completed.", invocation.getMethod().getName());

        return result;
    }
}
