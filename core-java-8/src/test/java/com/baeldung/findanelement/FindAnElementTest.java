package com.baeldung.findanelement;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class FindAnElementTest {

    private static List<Integer> scores = new ArrayList<>();
    static {
        scores.add(new Integer(0));
        scores.add(new Integer(1));
        scores.add(new Integer(2));
    }

    private static FindElementInAList<Integer> findElementInAList = new FindElementInAList<>();

    @Test
    public void givenElement_whenFoundUsingIndexOf_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingIndexOf(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingListIterator_thenReturnNull() {
        Integer score = findElementInAList.findUsingListIterator(new Integer(5), scores);
        assertNull(score);
    }
    
    @Test
    public void givenElement_whenFoundListIterator_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingListIterator(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingIndexOf_thenReturnNull() {
        Integer score = findElementInAList.findUsingIndexOf(new Integer(5), scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingEnhancedForLoop_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingEnhancedForLoop(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingEnhancedForLoop_thenReturnNull() {
        Integer scoreToFind = new Integer(5);
        Integer score = findElementInAList.findUsingEnhancedForLoop(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingStream_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingStream(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingStream_thenReturnNull() {
        Integer scoreToFind = new Integer(5);
        Integer score = findElementInAList.findUsingStream(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingParallelStream_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingParallelStream(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingParallelStream_thenReturnNull() {
        Integer scoreToFind = new Integer(5);
        Integer score = findElementInAList.findUsingParallelStream(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingGuava_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingGuava(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingGuava_thenReturnNull() {
        Integer scoreToFind = new Integer(5);
        Integer score = findElementInAList.findUsingGuava(scoreToFind, scores);
        assertNull(score);
    }

    @Test
    public void givenElement_whenFoundUsingApacheCommons_thenReturnElement() {
        Integer scoreToFind = new Integer(1);
        Integer score = findElementInAList.findUsingApacheCommon(scoreToFind, scores);
        assertTrue(score.equals(scoreToFind));
    }

    @Test
    public void givenElement_whenNotFoundUsingApacheCommons_thenReturnNull() {
        Integer scoreToFind = new Integer(5);
        Integer score = findElementInAList.findUsingApacheCommon(scoreToFind, scores);
        assertNull(score);
    }

}