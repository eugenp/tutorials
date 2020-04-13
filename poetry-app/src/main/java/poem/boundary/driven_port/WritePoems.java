package poem.boundary.driven_port;

/**
 * Driven, right side port for writing the lines of a poem to an output device
 * outside the hexagon, e.g. the console.
 *
 */
public interface WritePoems {
	void writeLines(String[] strings);
}