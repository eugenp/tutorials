package movie;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceAdapterTest {

    private static MovieServiceAdapter movieServiceAdapter;

    @BeforeAll
    private static void setup() {
        movieServiceAdapter = new MovieServiceAdapter();
    }

    @Test
    public void getByTitleReturnsAMovie() {
        Movie movie = movieServiceAdapter.getByTitle("unit test");
        assertTrue(movie instanceof Movie);
        assertNotNull(movie);
    }

    @Test
    public void searchByTitleReturnsAnEmptyList() {
        List<Movie> movies = movieServiceAdapter.searchByTitle("unit tests");
        assertTrue(movies instanceof ArrayList);
        assertEquals(movies.size(), 0);
    }

    @Test
    public void addReturnsAMovie() {
        Movie movie = movieServiceAdapter.add(new Movie());
        assertTrue(movie instanceof Movie);
        assertNotNull(movie);
    }
}
