package poem.simple.driven_adapter;

import java.util.Objects;

import poem.boundary.driven_port.WritePoems;

/**
 * Right-side, driven adapter for writing text to the console.
 *
 */
public class ConsoleWriter implements WritePoems {
	public void writeLines(String[] lines) {
		Objects.requireNonNull(lines);
		for (String line : lines) {
			System.out.println(line);
		}
		System.out.println("");
	}
}