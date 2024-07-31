package com.baeldung.embeddable.repositories;

import com.baeldung.embeddable.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    List<Company> findByContactPersonFirstName(String firstName);

    @Query("SELECT C FROM Company C WHERE C.contactPerson.firstName = ?1")
    List<Company> findByContactPersonFirstNameWithJPQL(String firstName);

    @Query(value = "SELECT * FROM \"company\" WHERE \"contact_first_name\" = ?1", nativeQuery = true)
    List<Company> findByContactPersonFirstNameWithNativeQuery(String firstName);
}
