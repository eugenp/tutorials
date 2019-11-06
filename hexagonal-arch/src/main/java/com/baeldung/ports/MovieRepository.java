package com.baeldung.ports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.vo.Movie;

/**
 * Secondary Port
 * @author rchaudhary23
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	public List<Movie> findByYearOfRelease(String year);

	public List<Movie> findByMovieGenreAndYearOfRelease(String genre, String year);

	public List<Movie> findByMovieGenre(String genre);
}
