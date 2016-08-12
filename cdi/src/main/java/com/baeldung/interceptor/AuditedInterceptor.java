package com.baeldung.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

@Audited @Interceptor
public class AuditedInterceptor {
    public static boolean calledBefore = false;
    public static boolean calledAfter = false;
    @AroundInvoke
    public Object auditMethod(InvocationContext ctx) throws Exception {
        calledBefore = true;
        Object result = ctx.proceed();
        calledAfter = true;
        return result;
    }
}
