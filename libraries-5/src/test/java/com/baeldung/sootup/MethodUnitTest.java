package com.baeldung.sootup;

import org.junit.jupiter.api.Test;
import sootup.core.IdentifierFactory;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SootClass;
import sootup.core.model.SootMethod;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.OTFCompileAnalysisInputLocation;
import sootup.java.core.views.JavaView;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodUnitTest {

    @Test
    void whenAnalyzingThisClass_thenWeCanAccessMethods() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/MethodUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.MethodUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Set<? extends SootMethod> methods = sootClass.getMethods();
        assertThat(methods).isNotEmpty();
    }

    @Test
    void whenAnalyzingThisClass_thenWeCanAccessASingleMethod() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/MethodUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.MethodUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Optional<? extends SootMethod> method = sootClass.getMethod("someMethod",
            List.of(
                identifierFactory.getClassType("java.lang.String")
            ));
        assertTrue(method.isPresent());

        SootMethod sootMethod = method.get();
        assertTrue(sootMethod.isPrivate());
        assertTrue(sootMethod.isConcrete());
    }

    @Test
    void whenAnalyzingThisClass_thenWeCanListMethodsByName() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/MethodUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.MethodUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Set<? extends SootMethod> method = sootClass.getMethodsByName("someMethod");
        assertEquals(1, method.size());
    }

    private void someMethod(String name) {}
}
