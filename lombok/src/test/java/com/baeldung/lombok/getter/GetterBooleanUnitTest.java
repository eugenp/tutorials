package com.baeldung.lombok.getter;

import org.junit.Test;
import static org.junit.Assert.*;

public class GetterBooleanUnitTest {

    @Test
    public void whenBasicBooleanField_thenMethodNamePrefixedWithIsFollowedByFieldName() {
        GetterbooleanTrivial lombokExamples = new GetterbooleanTrivial();
        assertFalse(lombokExamples.isRunning());
    }

    @Test
    public void whenBooleanFieldPrefixedWithIs_thenMethodNameIsSameAsFieldName() {
        GetterbooleanTricky lombokExamples = new GetterbooleanTricky();
        assertTrue(lombokExamples.isRunning());
    }

    @Test
    public void whenTwoBooleanFieldsCauseNamingConflict_thenLombokMapsToFirstDeclaredField() {
        GetterbooleanSameAccessor lombokExamples = new GetterbooleanSameAccessor();
        assertTrue(lombokExamples.isRunning() == lombokExamples.running);
        assertFalse(lombokExamples.isRunning() == lombokExamples.isRunning);
    }

    @Test
    public void whenFieldOfBooleanType_thenLombokPrefixesMethodWithGetInsteadOfIs() {
        GetterBoolean lombokExamples = new GetterBoolean();
        assertTrue(lombokExamples.getRunning());
    }
}
