package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 来自： https://www.programcreek.com/java-api-examples/?code=PacktPublishing/Spring-5.0-Cookbook/Spring-5.0-Cookbook-master/Chapter05/ch05/src/main/java/org/packt/aop/transaction/core/InterceptReqControllerAspect.java
 */
@Component
@Aspect
public class InterceptReqControllerAspect {
    private Logger logger = LoggerFactory.getLogger(InterceptReqControllerAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Controller *)") //we define a pointcut for all controllers
    public void classPointcut() {}

    @Pointcut("execution(* *intercept(..))") // the methods that ends in getViewNew are join to this pointcut
    public void methodPointcut() {}

    /**
     * Operations*/

    @Around("classPointcut() && methodPointcut()")
    public String logRequest(ProceedingJoinPoint joinPointp) throws Throwable {


        System.out.println("Starting request...");
        logger.info("Starting request...");
        joinPointp.proceed();
        System.out.println("Stopping request");
        logger.info("Stopping request...");
        return "Hello Ortigas";
    }

}