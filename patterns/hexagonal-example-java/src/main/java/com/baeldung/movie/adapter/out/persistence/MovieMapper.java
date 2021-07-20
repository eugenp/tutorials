package com.baeldung.movie.adapter.out.persistence;



import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.baeldung.movie.domain.MovieDto;


@Mapper
public interface MovieMapper {
    
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);
    
    MovieDto movieToMovieDto(Movie movie);
    
    Movie movieDtoToMovie(MovieDto movieDto);
    
    List<MovieDto> movieListToMovieDtoList(List<Movie> movieList);

    List<Movie> movieDtoListToMovieList(List<MovieDto> movieDtoList);
}
