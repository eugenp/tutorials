package com.baeldung.springpagination.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.baeldung.springpagination.model.Subject;

public interface SubjectRepository extends PagingAndSortingRepository<Subject, Long> {

    @RestResource(path = "nameContains")
    public Page<Subject> findByNameContaining(@Param("name") String name, Pageable p);

}