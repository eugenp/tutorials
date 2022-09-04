package com.baeldung.methodinfo;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Logger;

@Aspect
@Component
public class BankAccountAspect {

        private static Logger logger = Logger.getLogger(BankAccountAspect.class.getName());

        @Before(value = "@annotation(com.baeldung.methodinfo.AccountOperation)")
        public void getAccountOperationInfo(JoinPoint joinPoint) {

                // Method Information
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();

                logger.info("full method description: " + signature.getMethod());
                logger.info("method name: " + signature.getMethod().getName());
                logger.info("declaring type: " + signature.getDeclaringType());

                // Method args
                logger.info("Method args names:");
                Arrays.stream(signature.getParameterNames())
                                .forEach(s -> logger.info("arg name: " + s));
                logger.info("Method args types:");
                Arrays.stream(signature.getParameterTypes())
                                .forEach(s -> logger.info("arg type: " + s));
                logger.info("Method args values:");
                Arrays.stream(joinPoint.getArgs())
                                .forEach(o -> logger.info("arg value: " + o.toString()));

                // Additional Information
                logger.info("returning type: " + signature.getReturnType());
                logger.info("method modifier: " + Modifier.toString(signature.getModifiers()));
                Arrays.stream(signature.getExceptionTypes())
                                .forEach(aClass -> logger.info("exception type: " + aClass));

                // Method annotation
                Method method = signature.getMethod();
                AccountOperation accountOperation = method.getAnnotation(AccountOperation.class);
                logger.info("Account operation annotation: " + accountOperation);
                logger.info("Account operation value: " + accountOperation.operation());

        }
}
