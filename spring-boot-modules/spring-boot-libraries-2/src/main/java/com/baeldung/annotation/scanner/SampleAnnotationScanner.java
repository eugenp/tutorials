package com.baeldung.annotation.scanner;

import java.util.List;

public interface SampleAnnotationScanner {
    List<String> scanAnnotatedMethods();

    List<String> scanAnnotatedClasses();

    boolean supportsMethodScan();

    boolean supportsClassScan();
}
