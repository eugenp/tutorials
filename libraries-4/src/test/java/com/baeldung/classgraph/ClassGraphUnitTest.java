package com.baeldung.classgraph;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.Test;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.Resource;
import io.github.classgraph.ResourceList;
import io.github.classgraph.ScanResult;

public class ClassGraphUnitTest {

    @Test
    public void whenClassAnnotationFilterIsDefined_thenTargetClassesCanBeFound() {
        doTest(result -> {
            ClassInfoList classInfos = result.getClassesWithAnnotation(TestAnnotation.class.getName());
            assertThat(classInfos).extracting(ClassInfo::getName).contains(ClassWithAnnotation.class.getName());
        });
    }

    @Test
    public void whenMethodAnnotationFilterIsDefined_thenTargetClassesCanBeFound() {
        doTest(result -> {
            ClassInfoList classInfos = result.getClassesWithMethodAnnotation(TestAnnotation.class.getName());
            assertThat(classInfos).extracting(ClassInfo::getName).contains(MethodWithAnnotation.class.getName());
        });
    }

    @Test
    public void whenMethodAnnotationValueFilterIsDefined_thenTargetClassesCanBeFound() {
        doTest(result -> {
            ClassInfoList classInfos = result.getClassesWithMethodAnnotation(TestAnnotation.class.getName());
            ClassInfoList filteredClassInfos = classInfos.filter(classInfo -> {
                return classInfo.getMethodInfo().stream().anyMatch(methodInfo -> {
                    AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(TestAnnotation.class.getName());
                    if (annotationInfo == null) {
                        return false;
                    }

                    return "web".equals(annotationInfo.getParameterValues().getValue("value"));
                });
            });
            assertThat(filteredClassInfos)
                    .extracting(ClassInfo::getName)
                    .contains(MethodWithAnnotationParameterWeb.class.getName());
        });
    }

    @Test
    public void whenFieldAnnotationFilterIsDefined_thenTargetClassesCanBeFound() {
        doTest(result -> {
            ClassInfoList classInfos = result.getClassesWithFieldAnnotation(TestAnnotation.class.getName());
            assertThat(classInfos).extracting(ClassInfo::getName).contains(FieldWithAnnotation.class.getName());
        });
    }

    @Test
    public void whenResourceIsUsed_thenItCanBeFoundAndLoaded() throws IOException {
        try (ScanResult result = new ClassGraph().whitelistPaths("classgraph").scan()) {
            ResourceList resources = result.getResourcesWithExtension("config");
            assertThat(resources).extracting(Resource::getPath).containsOnly("classgraph/my.config");
            assertThat(resources.get(0).getContentAsString()).isEqualTo("my data");
        }
    }

    private void doTest(Consumer<ScanResult> checker) {
        try (ScanResult result = new ClassGraph().enableAllInfo()
                                                 .whitelistPackages(getClass().getPackage().getName())
                                                 .scan())
        {
            checker.accept(result);
        }
    }
}