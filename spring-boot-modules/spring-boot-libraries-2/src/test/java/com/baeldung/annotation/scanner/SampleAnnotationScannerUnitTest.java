package com.baeldung.annotation.scanner;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleAnnotationScannerUnitTest {
    @Autowired
    private List<SampleAnnotationScanner> scannerList;

    @Test
    public void givenPackage_whenScanAnnotatedClasses_thenAnnotationValues() {
        final List<String> annotatedClasses = scannerList.stream()
            .filter(SampleAnnotationScanner::supportsClassScan)
            .map(SampleAnnotationScanner::scanAnnotatedClasses)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        assertNotNull(annotatedClasses);
        assertEquals(4, annotatedClasses.size());
        annotatedClasses.forEach(annotValue -> assertEquals("SampleAnnotatedClass", annotValue));
    }

    @Test
    public void givenPackage_whenScanAnnotatedMethods_thenAnnotationValues() {
        final List<String> annotatedMethods = scannerList.stream()
            .filter(SampleAnnotationScanner::supportsMethodScan)
            .map(SampleAnnotationScanner::scanAnnotatedMethods)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        assertNotNull(annotatedMethods);
        assertEquals(3, annotatedMethods.size());
        annotatedMethods.forEach(annotValue -> assertEquals("annotatedMethod", annotValue));
    }

}
