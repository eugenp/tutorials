package com.baeldung.jpa.projection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import com.baeldung.jpa.projection.model.Person;
import com.baeldung.jpa.projection.view.PersonDto;
import com.baeldung.jpa.projection.view.PersonView;

public interface PersonRepository extends Repository<Person, Long> {
    PersonView findByLastName(String lastName);

    PersonDto findByFirstName(String firstName);

    <T> T findByLastName(String lastName, Class<T> type);

    @Query(name = "person_native_query_dto", nativeQuery = true)
    List<PersonDto> findByFirstNameLike(@Param("firstNameLike") String firstNameLike);
}
