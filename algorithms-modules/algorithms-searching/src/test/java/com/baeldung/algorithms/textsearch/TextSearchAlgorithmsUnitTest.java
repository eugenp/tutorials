package com.baeldung.algorithms.textsearch;


import org.junit.Assert;
import org.junit.Test;

public class TextSearchAlgorithmsUnitTest {


    @Test
    public void testStringSearchAlgorithms() {
        String text = "This is some nice text.";
        String pattern = "some";

        int realPosition = text.indexOf(pattern);
        Assert.assertTrue(realPosition == TextSearchAlgorithms.simpleTextSearch(pattern.toCharArray(), text.toCharArray()));
        Assert.assertTrue(realPosition == TextSearchAlgorithms.RabinKarpMethod(pattern.toCharArray(), text.toCharArray()));
        Assert.assertTrue(realPosition == TextSearchAlgorithms.KnuthMorrisPrattSearch(pattern.toCharArray(), text.toCharArray()));
        Assert.assertTrue(realPosition == TextSearchAlgorithms.BoyerMooreHorspoolSimpleSearch(pattern.toCharArray(), text.toCharArray()));
        Assert.assertTrue(realPosition == TextSearchAlgorithms.BoyerMooreHorspoolSearch(pattern.toCharArray(), text.toCharArray()));
    }

}
