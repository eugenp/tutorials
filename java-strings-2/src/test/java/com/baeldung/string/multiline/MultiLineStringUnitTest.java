package com.baeldung.string.multiline;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import com.baeldung.string.multiline.MultiLineString;

public class MultiLineStringUnitTest {

    
    @Test
    public void whenCompareMultiLineStrings_thenTheyAreAllTheSame() throws IOException {
        MultiLineString ms = new MultiLineString();
        assertEquals(ms.stringConcatenation(), ms.stringJoin());
        assertEquals(ms.stringJoin(), ms.stringBuilder());
        assertEquals(ms.stringBuilder(), ms.guavaJoiner());
        assertEquals(ms.guavaJoiner(), ms.loadFromFile());
    }
    
}
