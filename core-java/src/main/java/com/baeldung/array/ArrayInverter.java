package com.baeldung.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class ArrayInverter {

	public void invertUsingFor(Object[] array) {
		for (int i = 0; i < array.length / 2; i++) {
			Object temp = array[i];
			array[i] = array[array.length - 1 - i];
			array[array.length - 1 - i] = temp;
		}
	}

	public void invertUsingCollectionsReverse(Object[] array) {
		List<Object> list = Arrays.asList(array);
		Collections.reverse(list);
	}

	public Object[] invertUsingStreams(final Object[] array) {
		return IntStream.
				range(1, array.length + 1).
				mapToObj(i -> array[array.length - i]).
				toArray();
	}

}
