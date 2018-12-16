package com.baeldung.string;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReplaceCharInStringUnitTest {
    private ReplaceCharacterInString characterInString = new ReplaceCharacterInString();

    @Test
    public void replaceCarAtIndex(){
        assertEquals("abcme",characterInString.replaceCharSubstring("abcde",'m',3));
    }

    @Test
    public void replaceCarAtIndexStringBuilder(){
        assertEquals("abcme",characterInString.replaceCharStringBuilder("abcde",'m',3));
    }

    @Test
    public void replaceCarAtIndexStringBuffer(){
        assertEquals("abcme",characterInString.replaceCharStringBuffer("abcde",'m',3));
    }
}
