
package com.baeldung.guice.aop;

import java.util.logging.Logger;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author baeldung
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
