package com.baeldung.architecture.hexagonal.common.log;

import com.baeldung.architecture.hexagonal.common.annotation.MethodLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Aspect
@Component
public class MethodLoggerAspect {

    @Pointcut("@annotation(com.baeldung.architecture.hexagonal.common.annotation.MethodLogger)")
    public void methodLoggerPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Around(value = "methodLoggerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        String packageName = point.getSignature().getDeclaringTypeName();
        Class<?> pointClass = point.getTarget().getClass();

        LogLevel logLevel = getLogLevel(pointClass, method);

        String STAR = " ********** ";
        LogWriter.write(pointClass, logLevel, STAR + packageName + " " + method.getName() + "() start execution" + STAR);

        if (point.getArgs() != null && point.getArgs().length > 0) {
            StringBuilder sb = buildParameters(point, method);
            LogWriter.write(pointClass, logLevel, method.getName() + "() args Type: " + sb.getClass().getName() + " args: " + sb);
        }

        long startTime = System.currentTimeMillis();
        Object result = point.proceed();

        long endTime = System.currentTimeMillis();

        String resultType = result == null ? pointClass.getName() : result.getClass().getName();

        LogWriter.write(pointClass, logLevel, method.getName() + "() Result Type: " + resultType + " Result: " + result);

        LogWriter.write(pointClass, logLevel, STAR + packageName + " " + method.getName() + "() finished execution and takes " + (endTime - startTime) + " millis time to execute " + STAR);

        return result;
    }

    private LogLevel getLogLevel(Class<?> pointClass, Method method) {
        MethodLogger loggableMethod = method.getAnnotation(MethodLogger.class);
        MethodLogger loggableClass = pointClass.getAnnotation(MethodLogger.class);
        return loggableMethod != null ? loggableMethod.value() : loggableClass.value();
    }

    private StringBuilder buildParameters(ProceedingJoinPoint point, Method method) {
        Object[] pointArgs = point.getArgs();
        String pointArgsString = IntStream.range(0, pointArgs.length).mapToObj(i -> method.getParameterTypes()[i].getName() + ":" + pointArgs[i]).collect(Collectors.joining(", "));
        return new StringBuilder(pointArgsString);
    }

}
