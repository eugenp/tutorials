package com.baeldung.pointcutadvice;

import com.baeldung.pointcutadvice.events.FooCreationEvent;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PublishingAspect {

    private ApplicationEventPublisher eventPublisher;

    @Autowired
    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Pointcut("within(com.baeldung..*) && execution(* com.baeldung.pointcutadvice.dao.FooDao.*(..))")
    public void repositoryMethods() {
        // pointcut signatures
    }

    @Pointcut("within(com.baeldung..*) && execution(* com.baeldung.pointcutadvice.dao.FooDao.create*(Long,..))")
    public void firstLongParamMethods() {
        // pointcut signatures
    }

    @Pointcut("repositoryMethods() && firstLongParamMethods()")
    public void entityCreationMethods() {
        // pointcut signatures
    }

    @AfterReturning(value = "entityCreationMethods()", returning = "entity")
    public void logMethodCall(JoinPoint jp, Object entity) throws Throwable {
        eventPublisher.publishEvent(new FooCreationEvent(entity));
    }
}
