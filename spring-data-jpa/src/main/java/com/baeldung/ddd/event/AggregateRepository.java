/**
 *
 */
package com.baeldung.ddd.event;

import org.springframework.data.repository.CrudRepository;

public interface AggregateRepository extends CrudRepository<Aggregate, Long> {

}
