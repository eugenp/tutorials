package com.baeldung.lombok.requiredargsconstructor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baeldung.lombok.reuiredargsconstructor.ClassWithFinalMembers;
import com.baeldung.lombok.reuiredargsconstructor.ClassWithFinalNonNullMembers;
import com.baeldung.lombok.reuiredargsconstructor.ClassWithNonFinalMembers;

public class RequiredArgsConstructorUnitTest {
    @Test
    void givenFinalMember_whenInitializingClass_thenOutputIsCorrect() {
        ClassWithFinalMembers classWithFinalMembers = new ClassWithFinalMembers("dummyString");
        Assertions.assertNotNull(classWithFinalMembers);
        Assertions.assertEquals("dummyString", classWithFinalMembers.getStringObject());
    }

    @Test
    void givenNonFinalMember_whenInitializingClass_thenOutputIsCorrect() {
        ClassWithNonFinalMembers classWithNonFinalMembers = new ClassWithNonFinalMembers();
        Assertions.assertNotNull(classWithNonFinalMembers);
    }

    @Test
    void givenFinalAndNonNullAndNonFinalMembers_whenInitializingClass_thenOutputIsCorrect() {
        ClassWithFinalNonNullMembers classWithFinalNonNullMembers = new ClassWithFinalNonNullMembers("finalString", "nonNullString");
        Assertions.assertNotNull(classWithFinalNonNullMembers);
        Assertions.assertEquals("finalString", classWithFinalNonNullMembers.getFinalStringObject());
        Assertions.assertEquals("nonNullString", classWithFinalNonNullMembers.getNonNullStringObject());
        Assertions.assertNull(classWithFinalNonNullMembers.getNonFinalStringObject());
    }
}
