package com.example.aop;
//@formatter:off

import com.sun.istack.internal.Nullable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * This class is responsible for performing logging on Controller methods.
 * It used Spring AspectJ (not native Spring CGLib AOP) for weaving logging logic into matching controller methods.
 *
 * <p>This aspect uses two annotations - {@link Logging} and {@link NoLogging} to gain fine-grain control over
 * method logging behavior.
 *
 * @see <a href="http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html">
 *          Spring Documentation on Aspect Oriented Programming with Spring
 *      </a>
 */
//@formatter:on

@Aspect
public class GenericControllerAspect implements ControllerAspect {

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(GenericControllerAspect.class);

    @Pointcut("@annotation(io.github.logger.controller.annotation.Logging) " +
            "|| @target(io.github.logger.controller.annotation.Logging)")
    public void methodOrClassLoggingEnabledPointcut() {
    }

    @Pointcut("!@annotation(io.github.logger.controller.annotation.NoLogging)")
    public void methodLoggingNotDisabledPointcut() {
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) ||" +
            "within(@org.springframework.stereotype.Controller *)")
    public void allPublicControllerMethodsPointcut() {
    }

    @Around("allPublicControllerMethodsPointcut() "
            + "&& methodLoggingNotDisabledPointcut() "
            + "&& methodOrClassLoggingEnabledPointcut()")
    @Nullable
    public Object log( ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = null;
        String returnType = null;
        RequestMapping methodRequestMapping = null;
        RequestMapping classRequestMapping = null;

        try {
            MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
            methodRequestMapping = methodSignature.getMethod().getAnnotation(RequestMapping.class);
            classRequestMapping = proceedingJoinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);

            // this is required to distinguish between a returned value of null and no return value, as in case of
            // void return type.
            returnType = methodSignature.getReturnType().getName();

            logPreExecutionData(proceedingJoinPoint, methodRequestMapping);
        } catch (Exception e) {
            LOG.error("Exception occurred in pre-proceed logic", e);
        }

        StopWatch timer = new StopWatch();
        try {
            timer.start();
            result = proceedingJoinPoint.proceed();
        } finally {
            timer.stop();
            if (returnType != null) {
                logPostExecutionData(
                        proceedingJoinPoint, timer, result, returnType, methodRequestMapping, classRequestMapping
                );
            }
        }

        return result;
    }

    public void logPreExecutionData(
            @Nonnull ProceedingJoinPoint proceedingJoinPoint,
            @Nullable RequestMapping methodRequestMapping) {
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();

        String methodName = methodSignature.getName() + "()";
        Object argValues[] = proceedingJoinPoint.getArgs();
        String argNames[] = methodSignature.getParameterNames();
        String requestContext = RequestUtil.getRequestContext().toString();
        Annotation annotations[][] = methodSignature.getMethod().getParameterAnnotations();

        StringBuilder preMessage = new StringBuilder().append(methodName);

        if (argValues.length > 0) {
            logFunctionArguments(argNames, argValues, preMessage, annotations, methodRequestMapping);
        }

        preMessage.append(" called via ").append(requestContext);
        LOG.info(preMessage.toString());
    }

    public void logPostExecutionData(
            @Nonnull ProceedingJoinPoint proceedingJoinPoint,
            @Nonnull StopWatch timer,
            @Nullable Object result,
            @Nonnull String returnType,
            @Nullable RequestMapping methodRequestMapping,
            @Nullable RequestMapping classRequestMapping) {
        MethodSignature methodSignature = (MethodSignature)proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName() + "()";

        LOG.info(methodName + " took [" + timer.getTime() + " ms] to complete");

        if (LOG.isDebugEnabled()) {
            boolean needsSerialization = false;

            String produces[] = methodRequestMapping != null ? methodRequestMapping.produces() : new String[0];
            for (String produce : produces) {
                if (produce.equals(MediaType.APPLICATION_JSON_VALUE)) {
                    needsSerialization = true;
                    break;
                }
            }

            if (!needsSerialization) {
                produces = classRequestMapping != null ? classRequestMapping.produces() : new String[0];
                for (String produce : produces) {
                    if (produce.equals(MediaType.APPLICATION_JSON_VALUE)) {
                        needsSerialization = true;
                        break;
                    }
                }
            }

            StringBuilder postMessage = new StringBuilder().append(methodName).append(" returned: [");

            if (needsSerialization) {
                String resultClassName = result == null ? "null" : result.getClass().getName();
                resultClassName = returnType.equals("void") ? returnType : resultClassName;
                serialize(result, resultClassName, postMessage);
            } else {
                postMessage.append(result);
            }
            postMessage.append("]");
            LOG.debug(postMessage.toString());
        }
    }

    @AfterThrowing(
            pointcut = "allPublicControllerMethodsPointcut() && "
                    + "methodLoggingNotDisabledPointcut() && "
                    + "methodOrClassLoggingEnabledPointcut()",
            throwing = "t")
    public void onException(@Nonnull JoinPoint joinPoint, @Nonnull Throwable t) {
        String methodName = joinPoint.getSignature().getName() + "()";
        LOG.info(methodName + " threw exception: [" + t + "]");
    }

    public void serialize(@Nullable Object object, @Nonnull String objClassName, @Nonnull StringBuilder logMessage) {
        boolean serializedSuccessfully = false;
        Exception exception = null;

        // this is to distinguish between methods returning null value and methods returning void.
        // Object arg is null in both cases but objClassName is not.
        if (objClassName.toLowerCase().equals("void")) {
            logMessage.append(objClassName);
            serializedSuccessfully = true;
        }

        // try serializing assuming a perfectly serializable object.
        if (!serializedSuccessfully) {
            try {
                logMessage.append(JsonUtil.toJson(object));
                serializedSuccessfully = true;
            } catch (Exception e) {
                exception = e;
            }
        }

        // detect if its a mock object.
        if (!serializedSuccessfully && objClassName.toLowerCase().contains("mock")) {
            logMessage.append("Mock Object");
            serializedSuccessfully = true;
        }

        // try getting file size assuming object is a file type object
        if (!serializedSuccessfully) {
            long fileSize = -1;

            if (object instanceof ByteArrayResource) {
                fileSize = ((ByteArrayResource)object).contentLength();
            } else if (object instanceof MultipartFile) {
                fileSize = ((MultipartFile)object).getSize();
            }

            if (fileSize != -1) {
                logMessage.append("file of size:[").append(fileSize).append(" B]");
                serializedSuccessfully = true;
            }
        }

        if (!serializedSuccessfully) {
            LOG.warn("Unable to serialize object of type [{}] for logging", objClassName, exception);
        }
    }

    /**
     * Generated name-value pair of method's formal arguments. Appends the generated string in provided StringBuilder
     *
     * @param argNames String[] containing method's formal argument names Order of names must correspond to order on arg
     *            values in argValues.
     * @param argValues String[] containing method's formal argument values. Order of values must correspond to order on
     *            arg names in argNames.
     * @param stringBuilder the StringBuilder to append argument data to.
     */
    private void logFunctionArguments(
            @Nonnull String[] argNames,
            @Nonnull Object[] argValues,
            @Nonnull StringBuilder stringBuilder,
            @Nonnull Annotation annotations[][],
            @Nullable RequestMapping methodRequestMapping) {
        boolean someArgNeedsSerialization = false;

        if (methodRequestMapping != null) {
            for (String consumes : methodRequestMapping.consumes()) {
                if (consumes.equals(MediaType.APPLICATION_JSON_VALUE)) {
                    someArgNeedsSerialization = true;
                    break;
                }
            }
        }

        stringBuilder.append(" called with arguments: ");

        for (int i = 0, length = argNames.length; i < length; ++i) {
            boolean needsSerialization = false;

            if (someArgNeedsSerialization) {
                // We only need to serialize a param if @RequestBody annotation is found.
                for (Annotation annotation : annotations[i]) {
                    if (annotation instanceof RequestBody) {
                        needsSerialization = true;
                        break;
                    }
                }
            }

            stringBuilder.append(argNames[i]).append(": [");
            if (needsSerialization) {
                String argClassName = argValues[i] == null ? "NULL" : argValues[i].getClass().getName();
                serialize(argValues[i], argClassName, stringBuilder);
            } else {
                stringBuilder.append(argValues[i]);
            }
            stringBuilder.append("]").append(i == (length - 1) ? "" : ", ");
        }
    }
}
