package de.samples.quarkus.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import io.quarkus.logging.Log;

@Interceptor
@LogMethodCall
public class LogMethodCallInterceptor {
	
	@AroundInvoke
	public Object doLogging(InvocationContext ctx) throws Exception {
		Log.info("METHOD CALL: " + ctx.getMethod().getName());
		return ctx.proceed();
	}
	

}
