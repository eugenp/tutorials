package com.baeldung.overridemethod.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingInvocationHandler implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        log.debug("PROXY LOG: Intercepting method: {}", method.getName());
        
        Object result = method.invoke(target, args);
        
        log.debug("PROXY LOG: Method {} executed.", method.getName());
        
        return result;
    }
}
