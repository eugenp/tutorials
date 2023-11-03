package com.baeldung.wrappingcharacterwise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.commons.text.WordUtils;
import org.junit.jupiter.api.Test;

public class WrapperUnitTest {
    
    Wrapper wrapper = new Wrapper();
    String lineSeparator = System.lineSeparator();
    
    @Test
    void givenStringWithLessThanNCharacters_whenWrapStringCharacterWise_thenUnchanged() {
        String input = "short sentence";
        assertEquals(input, wrapper.wrapStringCharacterWise(input, 20));
    }
    
    @Test
    void givenStringWithMoreThanNCharacters_whenWrapStringCharacterWise_thenCorrectlyWrapped() {
        String input = "Baeldung is a popular website that provides in-depth tutorials and articles on various programming and software development topics, primarily focused on Java and related technologies.";
        assertEquals("Baeldung is a\npopular website that\nprovides in-depth\ntutorials and\narticles on various\nprogramming and\nsoftware development\ntopics, primarily\nfocused on Java and\nrelated\ntechnologies.", wrapper.wrapStringCharacterWise(input, 20));
    }
    
    @Test
    void givenStringWithATooLongWord_whenWrapStringCharacterWise_thenThrows() {
        String input = "The word straightforward has more than 10 characters";
        assertThrows(IllegalArgumentException.class, () -> wrapper.wrapStringCharacterWise(input, 10));
    }
    
    @Test
    void givenStringWithLineReturns_whenWrapStringCharacterWise_thenWrappedAccordingly() {
        String input = "Baeldung\nis a popular website that provides in-depth tutorials and articles on various programming and software development topics, primarily focused on Java and related technologies.";
        assertEquals("Baeldung\nis a popular\nwebsite that\nprovides in-depth\ntutorials and\narticles on various\nprogramming and\nsoftware development\ntopics, primarily\nfocused on Java and\nrelated\ntechnologies.", wrapper.wrapStringCharacterWise(input, 20));
    }
    
    @Test
    void givenStringWithLessThanNCharacters_whenWrap_thenUnchanged() {
        String input = "short sentence";
        assertEquals(input, WordUtils.wrap(input, 20));
    }
    
    @Test
    void givenStringWithMoreThanNCharacters_whenWrap_thenCorrectlyWrapped() {
        String input = "Baeldung is a popular website that provides in-depth tutorials and articles on various programming and software development topics, primarily focused on Java and related technologies.";
        assertEquals("Baeldung is a" + lineSeparator + "popular website that" + lineSeparator + "provides in-depth" + lineSeparator + "tutorials and" + lineSeparator + "articles on various" + lineSeparator + "programming and" + lineSeparator + "software development" + lineSeparator + "topics, primarily" + lineSeparator + "focused on Java and" + lineSeparator + "related" + lineSeparator + "technologies.", WordUtils.wrap(input, 20));
    }
    
    @Test
    void givenStringWithATooLongWord_whenWrap_thenLongWordIsNotWrapped() {
        String input = "The word straightforward has more than 10 characters";
        assertEquals("The word" + lineSeparator + "straightforward" + lineSeparator + "has more" + lineSeparator + "than 10" + lineSeparator + "characters", WordUtils.wrap(input, 10));
    }
    
    @Test
    void givenStringWithLineReturns_whenWrap_thenWrappedLikeThereWasNone() {
        String input = "Baeldung" + lineSeparator + "is a popular website that provides in-depth tutorials and articles on various programming and software development topics, primarily focused on Java and related technologies.";
        assertEquals("Baeldung" + lineSeparator + "is a" + lineSeparator + "popular website that" + lineSeparator + "provides in-depth" + lineSeparator + "tutorials and" + lineSeparator + "articles on various" + lineSeparator + "programming and" + lineSeparator + "software development" + lineSeparator + "topics, primarily" + lineSeparator + "focused on Java and" + lineSeparator + "related" + lineSeparator + "technologies.", WordUtils.wrap(input, 20));
    }

}
