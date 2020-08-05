package com.baeldung.hexagonal.outbound.repo;

import com.baeldung.hexagonal.model.Movie;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepo extends CrudRepository<Movie, Long> {


}
