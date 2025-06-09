package com.baeldung.rawtype;

import java.util.ArrayList;
import java.util.List;

public class RawTypeDemo {

	public static void main(String[] args) {
		RawTypeDemo rawTypeDemo = new RawTypeDemo();
		rawTypeDemo.methodA();
	}

	public void methodA() {
		// parameterized type
		List<String> listStr = new ArrayList<>();
		listStr.add("Hello Folks!");
		methodB(listStr);
		String s = listStr.get(1); // ClassCastException at run time
	}

	public void methodB(List rawList) { // Inexpressive raw type
		rawList.add(1); // Unsafe operation
	}

}
