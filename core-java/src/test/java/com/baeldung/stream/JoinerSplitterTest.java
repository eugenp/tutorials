package com.baeldung.stream;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.streamApi.JoinerSplitter;

public class JoinerSplitterTest {

	@Test
	public void provided_array_convert_to_stream_and_convert_to_string() {

		String[] programming_languages = {"java", "python", "nodejs", "ruby"};

		String expectation = "java,python,nodejs,ruby";
		
		String result  = JoinerSplitter.join(programming_languages);
		assertEquals(result, expectation);
	}
	
	@Test
	public void provided_array_convert_to_stream_and_convert_to_prefixPostfixString() {
		String[] programming_languages = {"java", "python", 
				"nodejs", "ruby"};
		String expectation = "[java,python,nodejs,ruby]";
		
		String result  = JoinerSplitter.joinWithPrefixPostFix(programming_languages);
		assertEquals(result, expectation);
	}
	
	@Test
	public void provided_string_convert_to_stream_and_convert_to_listOfString() {
		String programming_languages = "java,python,nodejs,ruby";
		
		List<String> expectation = new ArrayList<String>();
		expectation.add("java");
		expectation.add("python");
		expectation.add("nodejs");
		expectation.add("ruby");
		
		List<String> result  = JoinerSplitter.split(programming_languages);
		
		assertEquals(result, expectation);
	}
  
	@Test
	public void provided_string_convert_to_stream_and_convert_to_listOfChar() {
		String programming_languages = "java,python,nodejs,ruby";
		
		List<Character> expectation = new ArrayList<Character>();
		char[] charArray = programming_languages.toCharArray();
		for (char c : charArray) {
			expectation.add(c);
		}
		
		List<Character> result  = JoinerSplitter.splitToListOfChar(programming_languages);
		assertEquals(result, expectation);
		
	}
  
}
