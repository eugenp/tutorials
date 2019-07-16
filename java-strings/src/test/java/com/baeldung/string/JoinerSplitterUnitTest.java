package com.baeldung.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JoinerSplitterUnitTest {

    @Test
    public void provided_array_convert_to_stream_and_convert_to_string() {

        String[] programming_languages = {"java", "python", "nodejs", "ruby"};

        String expectation = "java,python,nodejs,ruby";

        String result = JoinerSplitter.join(programming_languages);
        assertEquals(result, expectation);
    }

    @Test
    public void givenArray_transformedToStream_convertToPrefixPostfixString() {

        String[] programming_languages = {"java", "python",
          "nodejs", "ruby"};
        String expectation = "[java,python,nodejs,ruby]";

        String result = JoinerSplitter.joinWithPrefixPostFix(programming_languages);
        assertEquals(result, expectation);
    }

    @Test
    public void givenString_transformedToStream_convertToList() {

        String programming_languages = "java,python,nodejs,ruby";

        List<String> expectation = new ArrayList<String>();
        expectation.add("java");
        expectation.add("python");
        expectation.add("nodejs");
        expectation.add("ruby");

        List<String> result = JoinerSplitter.split(programming_languages);

        assertEquals(result, expectation);
    }

    @Test
    public void givenString_transformedToStream_convertToListOfChar() {

        String programming_languages = "java,python,nodejs,ruby";

        List<Character> expectation = new ArrayList<Character>();
        char[] charArray = programming_languages.toCharArray();
        for (char c : charArray) {
            expectation.add(c);
        }

        List<Character> result = JoinerSplitter.splitToListOfChar(programming_languages);
        assertEquals(result, expectation);

    }
    
    @Test
    public void givenStringArray_transformedToStream_convertToMap() {

        String[] programming_languages = new String[] {"language:java","os:linux","editor:emacs"};
        
        Map<String,String> expectation=new HashMap<>();
        expectation.put("language", "java");
        expectation.put("os", "linux");
        expectation.put("editor", "emacs");
        
        Map<String, String> result = JoinerSplitter.arrayToMap(programming_languages);
        assertEquals(result, expectation);
        
    }

}
