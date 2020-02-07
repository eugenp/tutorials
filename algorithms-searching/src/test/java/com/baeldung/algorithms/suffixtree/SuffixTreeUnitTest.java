package com.baeldung.algorithms.suffixtree;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuffixTreeUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuffixTreeUnitTest.class);

    private static SuffixTree suffixTree;

    @BeforeClass
    public static void setUp() {
        suffixTree = new SuffixTree();
        suffixTree.addText("bananabanana");
        printTree();
    }

    @Test
    public void givenSuffixTree_whenSearchingForA_thenReturn6Matches() {
        List<String> matches = suffixTree.searchText("a");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] { "b[a]nanabanana", "ban[a]nabanana", "banan[a]banana", "bananab[a]nana", "bananaban[a]na", "bananabanan[a]" }, matches.toArray());
    }

    @Test
    public void givenSuffixTree_whenSearchingForNab_thenReturn1Match() {
        List<String> matches = suffixTree.searchText("nab");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] { "bana[nab]anana" }, matches.toArray());
    }

    @Test
    public void givenSuffixTree_whenSearchingForNag_thenReturnNoMatches() {
        List<String> matches = suffixTree.searchText("nag");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] {}, matches.toArray());
    }

    @Test
    public void givenSuffixTree_whenSearchingForBanana_thenReturn2Matches() {
        List<String> matches = suffixTree.searchText("banana");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] { "[banana]banana", "banana[banana]" }, matches.toArray());
    }

    @Test
    public void givenSuffixTree_whenSearchingForNa_thenReturn4Matches() {
        List<String> matches = suffixTree.searchText("na");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] { "ba[na]nabanana", "bana[na]banana", "bananaba[na]na", "bananabana[na]" }, matches.toArray());
    }

    @Test
    public void givenSuffixTree_whenSearchingForX_thenReturnNoMatches() {
        List<String> matches = suffixTree.searchText("x");
        matches.stream()
            .forEach(m -> LOGGER.info(m));
        Assert.assertArrayEquals(new String[] {}, matches.toArray());
    }

    private static void printTree() {
        suffixTree.printTree();

        LOGGER.info("\n" + suffixTree.printTree());
        LOGGER.info("==============================================");
    }
}
