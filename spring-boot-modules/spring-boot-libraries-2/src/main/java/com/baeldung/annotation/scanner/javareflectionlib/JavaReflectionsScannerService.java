package com.baeldung.annotation.scanner.javareflectionlib;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.annotation.scanner.SampleAnnotation;
import com.baeldung.annotation.scanner.SampleAnnotationScanner;
import com.baeldung.annotation.scanner.UnexpectedScanException;

@Service
public class JavaReflectionsScannerService implements SampleAnnotationScanner {
    @Override
    public List<String> scanAnnotatedMethods() {
        try {
            Class<?> clazz = ClassLoader.getSystemClassLoader()
                .loadClass("com.baeldung.annotation.scanner.SampleAnnotatedClass");
            Method[] methods = clazz.getMethods();
            List<String> annotatedMethods = new ArrayList<>();
            for (Method method : methods) {
                SampleAnnotation annotation = method.getAnnotation(SampleAnnotation.class);
                if (annotation != null) {
                    annotatedMethods.add(annotation.name());
                }
            }
            return Collections.unmodifiableList(annotatedMethods);
        } catch (ClassNotFoundException e) {
            throw new UnexpectedScanException(e);
        }
    }

    @Override
    public List<String> scanAnnotatedClasses() {
        try {
            Class<?> clazz = ClassLoader.getSystemClassLoader()
                .loadClass("com.baeldung.annotation.scanner.SampleAnnotatedClass");
            List<String> annotatedClasses = new ArrayList<>();
            Annotation[] classAnnotations = clazz.getAnnotations();
            for (Annotation annotation : classAnnotations) {
                if (annotation.annotationType()
                    .equals(SampleAnnotation.class)) {
                    annotatedClasses.add(((SampleAnnotation) annotation).name());
                }
            }
            return Collections.unmodifiableList(annotatedClasses);
        } catch (ClassNotFoundException e) {
            throw new UnexpectedScanException(e);
        }
    }

    @Override
    public boolean supportsMethodScan() {
        return true;
    }

    @Override
    public boolean supportsClassScan() {
        return true;
    }
}
