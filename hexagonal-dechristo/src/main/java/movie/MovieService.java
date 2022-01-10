package movie;

import java.util.List;

public interface MovieService {
    Movie getByTitle(String title);
    List<Movie> searchByTitle(String title);
    Movie add(Movie movie);
}
