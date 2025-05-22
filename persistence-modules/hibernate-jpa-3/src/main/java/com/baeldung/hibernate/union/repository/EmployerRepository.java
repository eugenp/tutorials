package com.baeldung.hibernate.union.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.union.dto.PersonView;
import com.baeldung.hibernate.union.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query(value = "select e.id, e.name, e.entity from unified_person_view e", nativeQuery = true)
    List<PersonView> findPersonView();
}
