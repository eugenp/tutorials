package com.baeldung.sootup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import sootup.core.IdentifierFactory;
import sootup.core.inputlocation.AnalysisInputLocation;
import sootup.core.model.SootClass;
import sootup.core.model.SootField;
import sootup.core.types.ClassType;
import sootup.java.bytecode.inputlocation.OTFCompileAnalysisInputLocation;
import sootup.java.core.views.JavaView;

public class FieldUnitTest {
    private String aField;

    @Test
    void whenAnalyzingThisClass_thenWeCanAccessFields() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/FieldUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.FieldUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Set<? extends SootField> fields = sootClass.getFields();
        assertEquals(1, fields.size());
    }

    @Test
    void whenAnalyzingThisClass_thenWeCanAccessASingleField() {
        Path javaFile = Path.of("src/test/java/com/baeldung/sootup/FieldUnitTest.java");
        AnalysisInputLocation inputLocation = new OTFCompileAnalysisInputLocation(javaFile);

        JavaView view = new JavaView(inputLocation);

        IdentifierFactory identifierFactory = view.getIdentifierFactory();
        ClassType javaClass = identifierFactory.getClassType("com.baeldung.sootup.FieldUnitTest");

        SootClass sootClass = view.getClassOrThrow(javaClass);
        Optional<? extends SootField> field = sootClass.getField("aField");
        assertTrue(field.isPresent());

        SootField sootField = field.get();
        assertTrue(sootField.isPrivate());
        assertFalse(sootField.isStatic());
    }
}
