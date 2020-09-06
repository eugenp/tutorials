package com.baeldung.comparators;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import com.baeldung.compareTo.comparators.PersonIdComparator;
import com.baeldung.compareTo.comparators.PersonNameComparator;
import com.baeldung.compareTo.model.Person;

public class PersonComparatorUnitTest {
	public static void sortIntegerArrayList() {

		ArrayList<Integer> list = new ArrayList<>();
		list.add(21);
		list.add(12);
		list.add(23);
		list.add(31);

		ArrayList<Integer> sortedList = new ArrayList<>();
		sortedList.add(12);
		sortedList.add(21);
		sortedList.add(23);
		sortedList.add(31);

		Collections.sort(list);
		Assert.assertArrayEquals(list.toArray(), sortedList.toArray());
	}

	@Test
	public  void sortPersonWithComparable() {
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new Person(1, "John"));
		list.add(new Person(3, "Andy"));
		list.add(new Person(2, "Kallis"));
		list.add(new Person(5, "Karen"));
		list.add(new Person(8, "Josh"));

		ArrayList<Person> idSortedList = new ArrayList<Person>();
		idSortedList.add(new Person(1, "John"));
		idSortedList.add(new Person(2, "Kallis"));
		idSortedList.add(new Person(3, "Andy"));
		idSortedList.add(new Person(5, "Karen"));
		idSortedList.add(new Person(8, "Josh"));

		Collections.sort(list);
		Assert.assertArrayEquals(list.toArray(), idSortedList.toArray());

	}

	@Test
	public  void sortPersonWithComparator() {
		ArrayList<Person> list = new ArrayList<Person>();
		list.add(new Person(1, "John"));
		list.add(new Person(3, "Andy"));
		list.add(new Person(2, "Kallis"));
		list.add(new Person(5, "Karen"));
		list.add(new Person(8, "Josh"));

		ArrayList<Person> idSortedList = new ArrayList<Person>();
		idSortedList.add(new Person(1, "John"));
		idSortedList.add(new Person(2, "Kallis"));
		idSortedList.add(new Person(3, "Andy"));
		idSortedList.add(new Person(5, "Karen"));
		idSortedList.add(new Person(8, "Josh"));

		Collections.sort(list, new PersonIdComparator());
		Assert.assertArrayEquals(list.toArray(), idSortedList.toArray());

		ArrayList<Person> nameSortedList = new ArrayList<Person>();
		nameSortedList.add(new Person(3, "Andy"));
		nameSortedList.add(new Person(1, "John"));
		nameSortedList.add(new Person(8, "Josh"));
		nameSortedList.add(new Person(2, "Kallis"));
		nameSortedList.add(new Person(5, "Karen"));

		Collections.sort(list, new PersonNameComparator());
		Assert.assertArrayEquals(list.toArray(), nameSortedList.toArray());

	}

//	public  void main(String args[]) {
//		sortIntegerArrayList();
//		sortPersonWithComparable();
//		sortPersonWithComparator();
//	}

}