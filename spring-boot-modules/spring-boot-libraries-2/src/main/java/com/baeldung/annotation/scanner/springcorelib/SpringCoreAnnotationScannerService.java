package com.baeldung.annotation.scanner.springcorelib;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.baeldung.annotation.scanner.SampleAnnotationScanner;
import com.baeldung.annotation.scanner.SampleAnnotatedClass;
import com.baeldung.annotation.scanner.SampleAnnotation;
import com.baeldung.annotation.scanner.ScanNotSupportedException;

@Service
public class SpringCoreAnnotationScannerService implements SampleAnnotationScanner {
    @Override
    public List<String> scanAnnotatedMethods() {
        final Class<?> userClass = ClassUtils.getUserClass(SampleAnnotatedClass.class);
        return Arrays.stream(userClass.getMethods())
            .filter(method -> AnnotationUtils.getAnnotation(method, SampleAnnotation.class) != null)
            .map(method -> method.getAnnotation(SampleAnnotation.class)
                .name())
            .collect(Collectors.toList());
    }

    @Override
    public List<String> scanAnnotatedClasses() {
        throw new ScanNotSupportedException();
    }

    @Override
    public boolean supportsMethodScan() {
        return true;
    }

    @Override
    public boolean supportsClassScan() {
        return false;
    }
}
