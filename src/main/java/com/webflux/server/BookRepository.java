package com.webflux.server;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends ReactiveCrudRepository<Book,Long>  {
	
	
}
