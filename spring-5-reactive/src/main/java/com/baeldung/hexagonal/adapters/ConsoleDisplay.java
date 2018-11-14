package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.domain.Display;

public class ConsoleDisplay implements Display{

	@Override
	public void show(String text) {
		System.out.print(text);
	}

}
