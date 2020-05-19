package com.baeldung.toggle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class FeaturesAspect {

    private static final Logger LOG = LogManager.getLogger(FeaturesAspect.class);

    @Around(value = "@within(featureAssociation) || @annotation(featureAssociation)")
    public Object checkAspect(ProceedingJoinPoint joinPoint, FeatureAssociation featureAssociation) throws Throwable {
        if (featureAssociation.value().isActive()) {
            return joinPoint.proceed();
        } else {
            LOG.info("Feature " + featureAssociation.value().name() + " is not enabled!");
            return null;
        }
    }

}
