package com.baeldung.testng.conditionalskip;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SkipAnnotationTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod != null && isTargetTestClass(testMethod) && shouldSkipTest()) {
            annotation.setEnabled(false);
        }
    }

    private boolean isTargetTestClass(Method testMethod) {
        return testMethod.getDeclaringClass().equals(SkipAnnotationTransformerUnitTest.class);
    }

    public boolean shouldSkipTest() {
        return true;
    }
}
