package com.baeldung.performancemonitor;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.springframework.aop.interceptor.AbstractMonitoringInterceptor;

import java.util.Date;

public class MyPerformanceMonitorInterceptor extends AbstractMonitoringInterceptor {
    
    public MyPerformanceMonitorInterceptor() {
    }

    public MyPerformanceMonitorInterceptor(boolean useDynamicLogger) {
            setUseDynamicLogger(useDynamicLogger);
    }

    @Override
    protected Object invokeUnderTrace(MethodInvocation invocation, Log log) throws Throwable {

        final String S = "Method ";
        
        String name = createInvocationTraceName(invocation);
        long start = System.currentTimeMillis();
        log.info(S + name+" execution started at:"+new Date());
        try {
            return invocation.proceed();
        }
        finally {
            long end = System.currentTimeMillis();
            long time = end - start;
            log.info(S + name +" execution lasted:" + time + " ms");
            log.info(S + name +" execution ended at:" + new Date());
            
            if (time > 10){
                log.warn("Method execution longer than 10 ms!");
            }
            
        }
    }
}
