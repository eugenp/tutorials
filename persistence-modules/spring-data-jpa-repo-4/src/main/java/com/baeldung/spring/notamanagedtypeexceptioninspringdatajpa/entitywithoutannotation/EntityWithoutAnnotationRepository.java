package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityWithoutAnnotationRepository
  extends JpaRepository<EntityWithoutAnnotation, Long> {

}
