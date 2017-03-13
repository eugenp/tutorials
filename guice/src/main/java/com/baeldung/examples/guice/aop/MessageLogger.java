package com.baeldung.examples.guice.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.logging.Logger;

/**
 *
 * @author Baeldung
 */
public class MessageLogger implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object[] objectArray = invocation.getArguments();
        int i = 0;
        for (Object object : objectArray) {
            Logger.getAnonymousLogger().info("Sending message: " + object.toString());
        }
        return invocation.proceed();
    }
}
