package com.baeldung.string;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReplaceCharInStringUnitTest {
    private ReplaceCharacterInString characterInString = new ReplaceCharacterInString();

    @Test
    public void whenReplaceCharAtIndex_UsingSubstring_thenSuccess(){
        assertEquals("abcme",characterInString.replaceCharSubstring("abcde",'m',3));
    }

    @Test
    public void whenReplaceCharAtIndex_UsingStringBuilder_thenSuccess(){
        assertEquals("abcme",characterInString.replaceCharStringBuilder("abcde",'m',3));
    }

    @Test
    public void whenReplaceCharAtIndex_UsingStringBuffer_thenSuccess(){
        assertEquals("abcme",characterInString.replaceCharStringBuffer("abcde",'m',3));
    }
}
