package com.baeldung.aspectj.classmethodadvice;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public aspect TracingAspect {
    private static final Log LOG = LogFactory.getLog(TracingAspect.class);

    pointcut traceAnnotatedClasses(): within(@Trace *) && execution(* *(..));

    Object around() : traceAnnotatedClasses() {
        String signature = thisJoinPoint.getSignature().toShortString();
        LOG.trace("Entering " + signature);
        try {
            return proceed();
        } catch (Exception e) {
            LOG.trace("Exception thrown from " + signature, e);
            throw e;
        } finally {
            LOG.trace("Exiting " + signature);
        }
    }
}
