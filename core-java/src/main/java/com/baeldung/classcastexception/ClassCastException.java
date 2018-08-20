package com.baeldung.classcastexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassCastException {

	public static void main(String[] args) {

		List<ArrayList<String>> strList = new ArrayList<ArrayList<String>>();
		// To correct the Exception at line #18, modify the line #11 code as:
		// List<List<Person>> personList = new ArrayList <List<Person>>();
		// Line #18 code as personList.add(Arrays.asList(personArray));
		String p1 = new String("John");
		String p2 = new String("Snow");
		String[] strArray = new String[] { p1, p2 };
		strList.add((ArrayList<String>) Arrays.asList(strArray));
		System.out.println("String list: " + strList);

	}

}
