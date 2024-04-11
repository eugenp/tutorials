package com.baeldung;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.Test;

public class OuterUnitTest {

    private static final String NEST_HOST_NAME = "com.baeldung.Outer";

    @Test
    public void whenGetNestHostFromOuter_thenGetNestHost() {
        is(Outer.class.getNestHost().getName()).equals(NEST_HOST_NAME);
    }

    @Test
    public void whenGetNestHostFromInner_thenGetNestHost() {
        is(Outer.Inner.class.getNestHost().getName()).equals(NEST_HOST_NAME);
    }

    @Test
    public void whenCheckNestmatesForNestedClasses_thenGetTrue() {
        assertTrue(Outer.Inner.class.isNestmateOf(Outer.class));
    }

    @Test
    public void whenCheckNestmatesForUnrelatedClasses_thenGetFalse() {
        assertFalse(Outer.Inner.class.isNestmateOf(Unrelated.class));
    }

    @Test
    public void whenGetNestMembersForNestedClasses_thenGetAllNestedClasses() {
        Set<String> nestMembers = Arrays.stream(Outer.Inner.class.getNestMembers())
          .map(Class::getName)
          .collect(Collectors.toSet());

        is(nestMembers.size()).equals(2);

        assertTrue(nestMembers.contains("com.baeldung.Outer"));
        assertTrue(nestMembers.contains("com.baeldung.Outer$Inner"));
    }
}