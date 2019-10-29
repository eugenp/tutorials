package com.baeldung.jupiter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.test.context.TestContextManager;
import org.springframework.util.Assert;

public class SpringExtension implements BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(SpringExtension.class);

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        getTestContextManager(context).beforeTestClass();
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        try {
            getTestContextManager(context).afterTestClass();
        } finally {
            context.getStore(namespace)
                .remove(context.getTestClass()
                    .get());
        }
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        getTestContextManager(context).prepareTestInstance(testInstance);
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance();
        Method testMethod = context.getTestMethod()
            .get();
        getTestContextManager(context).beforeTestMethod(testInstance, testMethod);
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance();
        Method testMethod = context.getTestMethod()
            .get();
        Throwable testException = context.getExecutionException()
            .orElse(null);
        getTestContextManager(context).afterTestMethod(testInstance, testMethod, testException);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Executable executable = parameter.getDeclaringExecutable();
        return ((executable instanceof Constructor) && AnnotatedElementUtils.hasAnnotation(executable, Autowired.class)) || ParameterAutowireUtils.isAutowirable(parameter);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Parameter parameter = parameterContext.getParameter();
        Class<?> testClass = extensionContext.getTestClass()
            .get();
        ApplicationContext applicationContext = getApplicationContext(extensionContext);
        return ParameterAutowireUtils.resolveDependency(parameter, testClass, applicationContext);
    }

    private ApplicationContext getApplicationContext(ExtensionContext context) {
        return getTestContextManager(context).getTestContext()
            .getApplicationContext();
    }

    private TestContextManager getTestContextManager(ExtensionContext context) {
        Assert.notNull(context, "ExtensionContext must not be null");
        Class<?> testClass = context.getTestClass()
            .get();
        ExtensionContext.Store store = context.getStore(namespace);
        return store.getOrComputeIfAbsent(testClass, TestContextManager::new, TestContextManager.class);
    }
}
