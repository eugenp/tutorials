package com.baeldung.jupiter;

import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.test.context.TestContextManager;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class SpringExtension implements BeforeAllCallback, AfterAllCallback, TestInstancePostProcessor, BeforeEachCallback, AfterEachCallback, ParameterResolver {

    private static final ExtensionContext.Namespace namespace = ExtensionContext.Namespace.create(SpringExtension.class);

    @Override
    public void beforeAll(ContainerExtensionContext context) throws Exception {
        getTestContextManager(context).beforeTestClass();
    }

    @Override
    public void afterAll(ContainerExtensionContext context) throws Exception {
        try {
            getTestContextManager(context).afterTestClass();
        } finally {
            context
              .getStore(namespace)
              .remove(context
                .getTestClass()
                .get());
        }
    }

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        getTestContextManager(context).prepareTestInstance(testInstance);
    }

    @Override
    public void beforeEach(TestExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance();
        Method testMethod = context
          .getTestMethod()
          .get();
        getTestContextManager(context).beforeTestMethod(testInstance, testMethod);
    }

    @Override
    public void afterEach(TestExtensionContext context) throws Exception {
        Object testInstance = context.getTestInstance();
        Method testMethod = context
          .getTestMethod()
          .get();
        Throwable testException = context
          .getTestException()
          .orElse(null);
        getTestContextManager(context).afterTestMethod(testInstance, testMethod, testException);
    }

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();
        Executable executable = parameter.getDeclaringExecutable();
        return (executable instanceof Constructor && AnnotatedElementUtils.hasAnnotation(executable, Autowired.class)) || ParameterAutowireUtils.isAutowirable(parameter);
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Parameter parameter = parameterContext.getParameter();
        Class<?> testClass = extensionContext
          .getTestClass()
          .get();
        ApplicationContext applicationContext = getApplicationContext(extensionContext);
        return ParameterAutowireUtils.resolveDependency(parameter, testClass, applicationContext);
    }

    private ApplicationContext getApplicationContext(ExtensionContext context) {
        return getTestContextManager(context)
          .getTestContext()
          .getApplicationContext();
    }

    private TestContextManager getTestContextManager(ExtensionContext context) {
        Assert.notNull(context, "ExtensionContext must not be null");
        Class<?> testClass = context
          .getTestClass()
          .get();
        ExtensionContext.Store store = context.getStore(namespace);
        return store.getOrComputeIfAbsent(testClass, TestContextManager::new, TestContextManager.class);
    }
}
