package com.baeldung.projection.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.baeldung.projection.model.Person;
import com.baeldung.projection.view.PersonAddressDTO;
import com.baeldung.projection.view.PersonDto;
import com.baeldung.projection.view.PersonView;

public interface PersonRepository extends Repository<Person, Long> {
    PersonView findByLastName(String lastName);

    PersonDto findByFirstName(String firstName);

    <T> T findByLastName(String lastName, Class<T> type);
    
    @Query("select new com.baeldung.projection.view.PersonAddressDTO(p.firstName, a.city) from Person p inner join p.address a")
    List<PersonAddressDTO> findAllPersonsWithAddress();
}
