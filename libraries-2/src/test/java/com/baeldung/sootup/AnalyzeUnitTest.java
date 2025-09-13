package com.baeldung.sootup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.java.bytecode.inputlocation.JavaClassPathAnalysisInputLocation;
import sootup.java.bytecode.inputlocation.JrtFileSystemAnalysisInputLocation;
import sootup.java.bytecode.inputlocation.OTFCompileAnalysisInputLocation;
import sootup.java.core.views.JavaView;

public class AnalyzeUnitTest {
    @Test
    void whenAnalyzingTheJvm_thenWeCanListClasses() {
        AnalysisInputLocation inputLocation = new JrtFileSystemAnalysisInputLocation();

        JavaView view = new JavaView(inputLocation);

        assertThat(view.getClasses()).isNotEmpty();
    }

    @Test
    void whenAnalyzingThisTestClass_thenWeCanListClasses() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/AnalyzeUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        assertEquals(1, view.getClasses().size());
    }

    @Test
    void whenAnalyzingAString_thenWeCanListClasses() throws IOException {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/AnalyzeUnitTest.java");
        String javaContents = Files.readString(javaFile);

        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation("AnalyzeUnitTest.java", javaContents);

        JavaView view = new JavaView(inputLocation);

        assertEquals(1, view.getClasses().size());
    }

    @Test
    void whenAnalyzingCompiledByteCode_thenWeCanListClasses() {
        AnalysisInputLocation inputLocation = new JavaClassPathAnalysisInputLocation("target/classes");

        JavaView view = new JavaView(inputLocation);

        assertThat(view.getClasses()).isNotEmpty();
    }
}
