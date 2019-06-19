package com.baeldung.rawtype;

import java.util.ArrayList;
import java.util.List;

public class RawTypeDemo {

	public static void main(String[] args) {
		RawTypeDemo rawTypeDemo = new RawTypeDemo();
		rawTypeDemo.methodA();
	}

	public void methodA() {
		List<String> listStr = new ArrayList<>();
		listStr.add("Hello Folks!");
		methodB(listStr);
		String s = listStr.get(1);
	}

	public void methodB(List rawList) {
		rawList.add(1);
	}

}
