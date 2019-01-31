package com.baeldung.springboot.respository;

import com.baeldung.springboot.entity.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long >{

}
