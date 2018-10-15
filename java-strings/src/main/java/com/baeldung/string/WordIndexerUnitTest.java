package com.baeldung.string.searching;

import com.baeldung.string.searching.WordIndexer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class WordIndexerUnitTest {

    String theString;
    WordIndexer wordIndexer;

    @BeforeEach
    public void setUp() throws Exception {
        wordIndexer = new WordIndexer();

        theString = "To be, or not to be: that is the question: "
          + "Whether 'tis nobler in the mind to suffer "
          + "The slings and arrows of outrageous fortune, "
          + "Or to take arms against a sea of troubles, "
          + "And by opposing end them? To die: to sleep; "
          + "No more; and by a sleep to say we end "
          + "The heart-ache and the thousand natural shocks "
          + "That flesh is heir to, 'tis a consummation "
          + "Devoutly to be wish'd. To die, to sleep; "
          + "To sleep: perchance to dream: ay, there's the rub: "
          + "For in that sleep of death what dreams may come,";
    }

    @Test
    public void findWord() {
        Integer array[] = {7, 122, 130, 221, 438};
        List<Integer> expectedResult = Arrays.asList(array);

        ArrayList<Integer> actualResult = wordIndexer.findWord(theString, "or");

        assertEquals(expectedResult, actualResult);
    }
}