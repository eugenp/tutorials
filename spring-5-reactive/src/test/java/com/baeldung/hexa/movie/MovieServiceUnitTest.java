package com.baeldung.hexa.movie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;

import com.baeldung.hexa.InMemoryMovieRepository;
import com.baeldung.hexa.movie.domain.MovieDTO;
import com.baeldung.hexa.movie.domain.MovieRepository;
import com.baeldung.hexa.movie.domain.MovieService;
import com.baeldung.hexa.movie.domain.MovieServiceImpl;

public class MovieServiceUnitTest {

	@Test
	public void testAdingAndListingMovies() {
		
		MovieRepository movieRepository = new InMemoryMovieRepository();
		MovieService movieService = new MovieServiceImpl(movieRepository);
		
		movieService.addMovie(new MovieDTO("Star wars", 1978));
		movieService.addMovie(new MovieDTO("Funny movie", 1979));
		
		MovieDTO searchResult = movieService.findMovie("Star wars");
		assertNotNull(searchResult);
		assertEquals("Star wars", searchResult.getTitle());
		
		List<MovieDTO> searchAllResult = movieService.listMovies();
		assertEquals(2, searchAllResult.size());
	}	
	
	@Test
	public void testAdingAndSearchForMoviesNotExisted() {
		
		MovieRepository movieRepository = new InMemoryMovieRepository();
		MovieService movieService = new MovieServiceImpl(movieRepository);
		
		movieService.addMovie(new MovieDTO("Star wars", 1978));
		movieService.addMovie(new MovieDTO("Funny movie", 1979));
		
		MovieDTO searchResult = movieService.findMovie("Hulk");
		assertNull(searchResult);
	}	
}
