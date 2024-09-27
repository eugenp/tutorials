package com.baeldung.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Audited
@Interceptor
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
