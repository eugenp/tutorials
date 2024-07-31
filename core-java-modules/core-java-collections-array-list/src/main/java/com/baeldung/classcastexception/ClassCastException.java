package com.baeldung.classcastexception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassCastException {

	public static void main(String[] args) {

		String p1 = new String("John");
		String p2 = new String("Snow");
		String[] strArray = new String[] { p1, p2 };
		ArrayList<String> strList = (ArrayList<String>) Arrays.asList(strArray);
		// To fix the ClassCastException at above line, modify the code as:
		// List<String> strList = Arrays.asList(strArray);
		System.out.println("String list: " + strList);

	}

}
