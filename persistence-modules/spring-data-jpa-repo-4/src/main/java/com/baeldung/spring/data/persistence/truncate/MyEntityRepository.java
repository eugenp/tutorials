package com.baeldung.spring.data.persistence.truncate;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MyEntityRepository extends CrudRepository<MyEntity, Long> {

    @Modifying
    @Transactional
    // need to wrap in double quotes due to
    // spring.jpa.properties.hibernate.globally_quoted_identifiers=true backward compatibility
    // property disabled in tests
    @Query(value = "truncate table " + MyEntity.TABLE_NAME, nativeQuery = true)
    void truncateTable();
}
