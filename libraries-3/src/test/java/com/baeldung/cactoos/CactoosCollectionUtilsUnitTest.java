package com.baeldung.cactoos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;

public class CactoosCollectionUtilsUnitTest {

	@Test
	public void whenFilteredClassIsCalledWithSpecificArgs_thenCorrespondingFilteredCollectionShouldBeReturned() throws IOException {

		CactoosCollectionUtils obj = new CactoosCollectionUtils();

		// when
		List<String> strings = new ArrayList<String>() { 
            { 
                add("Hello"); 
                add("John"); 
                add("Smith"); 
                add("Eric");
                add("Dizzy");
            } 
        }; 
		int size = obj.getFilteredList(strings).size();

		// then
		assertEquals(3, size);

	}

}
