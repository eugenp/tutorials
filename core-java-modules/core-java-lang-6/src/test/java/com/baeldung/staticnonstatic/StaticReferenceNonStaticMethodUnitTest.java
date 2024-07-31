package com.baeldung.staticnonstatic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ToolBox {

    private String concat(String str1, String str2) {
        return str1 + str2;
    }

    private static String concatStatic(String str1, String str2) {
        return str1 + str2;
    }

    static String joinTwoStrings(String str1, String str2) {
        // return concat(str1, str2); //<-- compilation error
        return concatStatic(str1, str2);
    }

    static String creatingInstanceJoinTwoStrings(String str1, String str2) {
        ToolBox toolBox = new ToolBox();
        return toolBox.concat(str1, str2);
    }

    String instanceJoinTwoStrings(String str1, String str2) {
        return concat(str1, str2);
    }

    String instanceCallStaticJoinTwoStrings(String str1, String str2) {
        return concatStatic(str1, str2);
    }
}

public class StaticReferenceNonStaticMethodUnitTest {

    @Test
    void whenCallingStaticMethodFromStaticContext_thenGetExpectedResult() {
        assertEquals("ab", ToolBox.joinTwoStrings("a", "b"));
    }

    @Test
    void whenCallingInstanceMethodByCreatingAnInstance_thenGetExpectedResult() {
        assertEquals("ab", ToolBox.creatingInstanceJoinTwoStrings("a", "b"));
    }

    @Test
    void whenCallingInstanceMethodFromAnInstance_thenGetExpectedResult() {
        ToolBox toolBox = new ToolBox();
        assertEquals("ab", toolBox.instanceJoinTwoStrings("a", "b"));
    }

    @Test
    void whenCallingStaticMethodFromAnInstance_thenGetExpectedResult() {
        ToolBox toolBox = new ToolBox();
        assertEquals("ab", toolBox.instanceCallStaticJoinTwoStrings("a", "b"));
    }

}