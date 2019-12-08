package com.baeldung.hexagon.adapter;

import java.util.Objects;

import com.baeldung.hexagon.port.IOutput;

/**
 * Adapter for writing text to the console.
 *
 */
public class ConsoleOutput implements IOutput {
	public void output(String[] lines) {
		Objects.requireNonNull(lines);
		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println("");
	}
}