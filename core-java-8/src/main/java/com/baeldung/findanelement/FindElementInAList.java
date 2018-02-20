package com.baeldung.findanelement;

import java.util.List;
import java.util.Optional;
import org.apache.commons.collections4.IterableUtils;
import com.google.common.collect.Iterables;

public class FindElementInAList<T> {

	public T findUsingIndexOf(T element, List<T> listOfIntegers) {
		int index = listOfIntegers.indexOf(element);
		if (index >= 0) {
			return element;
		}
		return null;
	}

	public T findUsingNormalForLoop(T element, List<T> listOfIntegers) {
		for (int i = 0; i < listOfIntegers.size(); i++) {
			if (listOfIntegers.get(i).equals(element)) {
				return listOfIntegers.get(i);
			}
		}
		return null;
	}

	public T findUsingEnhancedForLoop(T element, List<T> listOfIntegers) {
		for (T elementFromList : listOfIntegers) {
			if (element.equals(elementFromList)) {
				return elementFromList;
			}
		}
		return null;
	}

	public T findUsingStream(T element, List<T> listOfIntegers) {
		Optional<T> foundElement = listOfIntegers
				.stream()
				.filter(integer -> integer.equals(element))
				.findFirst();
		return foundElement.isPresent() ? foundElement.get() : null;
	}

	public T findUsingParallelStream(T element, List<T> listOfIntegers) {
		Optional<T> foundElement = listOfIntegers
				.parallelStream()
				.filter(integer -> integer.equals(element))
				.findAny();
		return foundElement.isPresent() ? foundElement.get() : null;
	}

	public T findUsingGuava(T element, List<T> listOfIntegers) {
		T foundElement = Iterables
				.tryFind(listOfIntegers, currentelement -> currentelement.equals(element))
				.orNull();
		return foundElement;
	}

	public T findUsingApacheCommon(T element, List<T> listOfIntegers) {
		T foundElement = IterableUtils
				.find(listOfIntegers, currentelement -> currentelement.equals(element));
		return foundElement;
	}

}