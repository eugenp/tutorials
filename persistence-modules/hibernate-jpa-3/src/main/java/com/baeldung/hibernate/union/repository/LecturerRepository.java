package com.baeldung.hibernate.union.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baeldung.hibernate.union.model.Lecturer;
import com.baeldung.hibernate.union.model.PersonView;

@Repository
public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query(value = "select e.id, e.name, e.role from person_view e", nativeQuery = true)
    List<PersonView> findPersonView();
}
