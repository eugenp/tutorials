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
	public void provided_list_convert_to_stream_and_convert_to_list() {
		String programming_languages = "java,python,nodejs,ruby";
		
		List<String> expectation = new ArrayList<String>();
		expectation.add("java");
		expectation.add("python");
		expectation.add("nodejs");
		expectation.add("ruby");
		
		List<String> result  = JoinerSplitter.split(programming_languages);
		
		assertEquals(result, expectation);
	}
	
}
