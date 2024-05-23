package com.baeldung.backreferencevslookaround;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BackReferenceVsLookAroundUnitTest {

    @Test
    void givenStringWithAsterisksBetweenStringThenRemoveAsterisksUsingBackReference() {
        String str = "**te*xt**";
        String replaced = str.replaceAll("(^\\*)|(\\*$)|\\*", "$1$2");
        assertEquals("*text*", replaced);
    }

    @Test
    void givenStringWithAsterisksBetweenStringThenRemoveAsterisksUsingLookAround() {
        String str = "**te*xt**";
        String replacedUsingLookaround = str.replaceAll("(?<!^)\\*+(?!$)", "");
        assertEquals("*text*", replacedUsingLookaround);
    }

}
