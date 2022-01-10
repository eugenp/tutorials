package movie;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository {
    Movie getByTitle(String title);
    List<Movie> searchByTitle(String title);
    Movie add(Movie movie);
}
