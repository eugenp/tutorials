package com.baeldung.cdi.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;

@Audited @Interceptor
public class AuditedInterceptor {
    @AroundInvoke
    public Object auditMethod(InvocationContext ctx) throws Exception {
        Object[] parameters = ctx.getParameters();
        Method method= ctx.getMethod();
        String param = (String) parameters[0];
        System.out.println("Method "+method.getName()+" invoked with parameter "+param);
        Object result = ctx.proceed();
        System.out.println("Method "+method.getName()+" exit");
        return result;
    }
}
