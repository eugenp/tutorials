package com.baeldung.guava;

import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TypeTokenUnitTest {
    @Test
    public void whenCheckingIsAssignableFrom_shouldReturnTrueEvenIfGenericIsSpecified() throws Exception {
        ArrayList<String> stringList = new ArrayList<>();
        ArrayList<Integer> intList = new ArrayList<>();
        boolean isAssignableFrom = stringList.getClass().isAssignableFrom(intList.getClass());

        assertTrue(isAssignableFrom);
    }

    @Test
    public void whenCheckingIsSupertypeOf_shouldReturnFalseIfGenericIsSpecified() throws Exception {
        TypeToken<ArrayList<String>> listString = new TypeToken<ArrayList<String>>() {
        };
        TypeToken<ArrayList<Integer>> integerString = new TypeToken<ArrayList<Integer>>() {
        };

        boolean isSupertypeOf = listString.isSupertypeOf(integerString);

        assertFalse(isSupertypeOf);
    }

    @Test
    public void whenCheckingIsSubtypeOf_shouldReturnTrueIfClassIsExtendedFrom() throws Exception {
        TypeToken<ArrayList<String>> stringList = new TypeToken<ArrayList<String>>() {
        };
        TypeToken<List> list = new TypeToken<List>() {
        };

        boolean isSubtypeOf = stringList.isSubtypeOf(list);

        assertTrue(isSubtypeOf);
    }
}
