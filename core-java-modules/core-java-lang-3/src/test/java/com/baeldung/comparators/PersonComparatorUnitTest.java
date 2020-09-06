package com.baeldung.comparators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.compareTo.comparators.PersonIdComparator;
import com.baeldung.compareTo.comparators.PersonNameComparator;
import com.baeldung.compareTo.model.Person;

public class PersonComparatorUnitTest {
	static List<Integer> intList = Arrays.asList(21, 12, 23, 31);

	static List<Integer> sortedIntList = Arrays.asList(12, 21, 23, 31);

	static List<Person> personList = Arrays.asList(new Person(1, "John"), new Person(3, "Andy"),
			new Person(2, "Kallis"), new Person(5, "Karen"), new Person(8, "Josh"));

	static List<Person> idSortedPersonList = Arrays.asList(new Person(1, "John"), new Person(2, "Kallis"),
			new Person(3, "Andy"), new Person(5, "Karen"), new Person(8, "Josh"));

	static List<Person> nameSortedPersonList = Arrays.asList(new Person(3, "Andy"), new Person(1, "John"),
			new Person(8, "Josh"), new Person(2, "Kallis"), new Person(5, "Karen"));

	@Test
	public void sortIntegerArrayList() {
		Collections.sort(intList);
		Assert.assertArrayEquals(intList.toArray(), sortedIntList.toArray());
	}

	@Test
	public void sortPersonWithComparable() {
		Collections.sort(personList);
		Assert.assertArrayEquals(personList.toArray(), idSortedPersonList.toArray());
	}

	@Test
	public void sortPersonWithComparator() {

		Collections.sort(personList, new PersonIdComparator());
		Assert.assertArrayEquals(personList.toArray(), idSortedPersonList.toArray());

		Collections.sort(personList, new PersonNameComparator());
		Assert.assertArrayEquals(personList.toArray(), nameSortedPersonList.toArray());

	}

}