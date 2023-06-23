package com.baeldung.lombok.requiredargsconstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RequiredArgsConstructorUnitTest {
    @Test
    void whenClassHasFinalMembers_thenGeneratedConstructorHasParameters() {
        ClassWithFinalMembers classWithFinalMembers = new ClassWithFinalMembers("dummyString");
        Assertions.assertNotNull(classWithFinalMembers);
        Assertions.assertEquals("dummyString", classWithFinalMembers.getStringObject());
    }

    @Test
    void whenClassHasNonFinalMembers_thenGeneratedConstructorHasNoParameters() {
        ClassWithNonFinalMembers classWithNonFinalMembers = new ClassWithNonFinalMembers();
        Assertions.assertNotNull(classWithNonFinalMembers);
    }

    @Test
    void whenClassHasFinalAndNonNullMembers_thenGeneratedConstructorHasParameters() {
        ClassWithFinalNonNullMembers classWithFinalNonNullMembers = new ClassWithFinalNonNullMembers("finalString", "nonNullString");
        Assertions.assertNotNull(classWithFinalNonNullMembers);
        Assertions.assertEquals("finalString", classWithFinalNonNullMembers.getFinalStringObject());
        Assertions.assertEquals("nonNullString", classWithFinalNonNullMembers.getNonNullStringObject());
        Assertions.assertNull(classWithFinalNonNullMembers.getNonFinalStringObject());
    }
}
