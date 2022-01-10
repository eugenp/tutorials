package movie;

import java.util.List;

public class MovieServiceAdapter implements MovieService {

    private MovieService movieService;

    public MovieServiceAdapter(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public Movie getByTitle(String title) {
        return movieService.getByTitle(title);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return movieService.searchByTitle(title);
    }

    @Override
    public Movie add(Movie movie) {
        return movieService.add(movie);
    }
}
