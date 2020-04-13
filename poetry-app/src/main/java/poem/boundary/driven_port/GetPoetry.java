package poem.boundary.driven_port;

/**
 * Driven, right side port for obtaining poems, e.g. from a repository outside
 * the hexagon.
 *
 */
public interface GetPoetry {
	String[] getPoems(String authorName);
}