package com.baeldung.algorithms.textsearch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TextSearchAlgorithmsUnitTest {


    @Test
    void testStringSearchAlgorithms() {
        String text = "This is some nice text.";
        String pattern = "some";

        int realPosition = text.indexOf(pattern);
        assertEquals(TextSearchAlgorithms.simpleTextSearch(pattern.toCharArray(), text.toCharArray()), realPosition);
        assertEquals(TextSearchAlgorithms.RabinKarpMethod(pattern.toCharArray(), text.toCharArray()), realPosition);
        assertEquals(TextSearchAlgorithms.KnuthMorrisPrattSearch(pattern.toCharArray(), text.toCharArray()) , realPosition);
        assertEquals(TextSearchAlgorithms.BoyerMooreHorspoolSimpleSearch(pattern.toCharArray(), text.toCharArray()), realPosition);
        assertEquals(TextSearchAlgorithms.BoyerMooreHorspoolSearch(pattern.toCharArray(), text.toCharArray()), realPosition);
    }

}
