package com.baeldung.spring.notamanagedtypeexceptioninspringdatajpa.entitywithoutannotationfixed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityWithoutAnnotationFixedRepository
  extends JpaRepository<EntityWithoutAnnotationFixed, Long> {

}
