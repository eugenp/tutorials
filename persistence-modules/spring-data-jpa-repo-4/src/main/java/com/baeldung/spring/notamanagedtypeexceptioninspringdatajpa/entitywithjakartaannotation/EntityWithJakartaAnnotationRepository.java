package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithjakartaannotation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityWithJakartaAnnotationRepository extends JpaRepository<EntityWithJakartaAnnotation, Long> {

}
