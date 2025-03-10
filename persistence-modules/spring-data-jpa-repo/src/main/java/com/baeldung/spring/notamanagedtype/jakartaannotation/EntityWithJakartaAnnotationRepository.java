package com.baeldung.spring.notamanagedtype.jakartaannotation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityWithJakartaAnnotationRepository extends JpaRepository<EntityWithJakartaAnnotation, Long> {

}
