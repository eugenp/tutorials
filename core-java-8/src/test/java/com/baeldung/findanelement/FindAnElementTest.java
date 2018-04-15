package com.baeldung.findanelement;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FindAnElementTest {

    private static List<Integer> scores = new ArrayList<>();
    static {
        scores.add(0);
        scores.add(1);
        scores.add(2);
    }

    private static FindElementInAList<Integer> findElementInAList = new FindElementInAList<>();

    @Test
    public void givenElement_whenFoundUsingIndexOf_thenReturnElement() {
        Integer scoreToFind = 1;
        Integer score = findElementInAList.findUsingIndexOf(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingListIterator_thenReturnNull() {
        boolean found = findElementInAList.findUsingListIterator(5, scores);
        assertTrue(!found);
    }
    
    @Test
    public void givenElement_whenFoundListIterator_thenReturnElement() {
        Integer scoreToFind = 1;
        boolean found = findElementInAList.findUsingListIterator(scoreToFind, scores);
        assertTrue(found);
    }

    @Test
    public void givenElement_whenNotFoundUsingIndexOf_thenReturnNull() {
        Integer score = findElementInAList.findUsingIndexOf(5, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingEnhancedForLoop_thenReturnElement() {
        Integer scoreToFind = 1;
        boolean found = findElementInAList.findUsingEnhancedForLoop(scoreToFind, scores);
        assertTrue(found);
    }

    @Test
    public void givenElement_whenNotFoundUsingEnhancedForLoop_thenReturnNull() {
        Integer scoreToFind = 5;
        boolean found = findElementInAList.findUsingEnhancedForLoop(scoreToFind, scores);
        assertTrue(!found);
    }

    @Test
    public void givenElement_whenFoundUsingStream_thenReturnElement() {
        Integer scoreToFind = 1;
        Integer score = findElementInAList.findUsingStream(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingStream_thenReturnNull() {
        Integer scoreToFind = 5;
        Integer score = findElementInAList.findUsingStream(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingParallelStream_thenReturnElement() {
        Integer scoreToFind = 1;
        Integer score = findElementInAList.findUsingParallelStream(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingParallelStream_thenReturnNull() {
        Integer scoreToFind = 5;
        Integer score = findElementInAList.findUsingParallelStream(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingGuava_thenReturnElement() {
        Integer scoreToFind = 1;
        Integer score = findElementInAList.findUsingGuava(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingGuava_thenReturnNull() {
        Integer scoreToFind = 5;
        Integer score = findElementInAList.findUsingGuava(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingApacheCommons_thenReturnElement() {
        Integer scoreToFind = 1;
        Integer score = findElementInAList.findUsingApacheCommon(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingApacheCommons_thenReturnNull() {
        Integer scoreToFind = 5;
        Integer score = findElementInAList.findUsingApacheCommon(scoreToFind, scores);
        assertNull(score);
    }

}