package com.baeldung.lombok.builder;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class RequiredFieldAnnotationTest {
    RequiredFieldAnnotation requiredFieldTest;

    @BeforeEach
    void setUp() {
        requiredFieldTest = RequiredFieldAnnotation.builder("NameField").description("Field Description").build();
    }

    @Test
    public void givenBuilderWithRequiredParameter_thenParameterIsPresent() {
        assertEquals(requiredFieldTest.getName(), "NameField");
    }

}