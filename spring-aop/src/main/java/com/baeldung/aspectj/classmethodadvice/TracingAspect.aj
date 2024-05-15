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
        } finally {
            LOG.trace("Exiting " + signature);
        }
    }

    after() throwing (Exception e) : traceAnnotatedClasses() {
        LOG.trace("Exception thrown from " + thisJoinPoint.getSignature().toShortString(), e);
    }
}
