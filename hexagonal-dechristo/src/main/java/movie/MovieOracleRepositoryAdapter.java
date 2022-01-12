package movie;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieOracleRepositoryAdapter implements MovieRepository {
    private OracleRepository oracleRepository;

    public MovieOracleRepositoryAdapter(OracleRepository oracleRepository) {

        this.oracleRepository = oracleRepository;
    }

    @Override
    public Movie getByTitle(String title) {
        return oracleRepository.getByTitle(title);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return oracleRepository.searchByTitle(title);
    }

    @Override
    public Movie add(Movie movie) {
        return oracleRepository.add(movie);
    }
}
