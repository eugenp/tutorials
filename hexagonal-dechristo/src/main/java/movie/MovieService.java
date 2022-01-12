package movie;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MovieService {
    Movie getByTitle(String title);
    List<Movie> searchByTitle(String title);
    Movie add(Movie movie);
}
