package com.baeldung.listtostring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

public class LinkedListToStringUnitTest {
	@Test
	void givenList_toString_defaultFormat() {
		LinkedList<Integer> idsList = new LinkedList<>();
		idsList.add(101);
		idsList.add(102);
		idsList.add(103);

		assertEquals("[101, 102, 103]", idsList.toString(),
				"Default LinkedList toString() should match expected format.");
	}

	@Test
	void emptyList_toString_defaultFormat() {
		LinkedList<Integer> emptyList = new LinkedList<>();

		assertEquals("[]", emptyList.toString(), "Empty LinkedList should return empty brackets.");
	}
}
