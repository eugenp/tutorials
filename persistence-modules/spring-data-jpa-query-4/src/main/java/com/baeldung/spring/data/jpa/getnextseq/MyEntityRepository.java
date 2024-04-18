package com.baeldung.spring.data.jpa.getnextseq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {

    @Query(value = "SELECT NEXTVAL('my_sequence_name')", nativeQuery = true)
    Long getNextSequenceValue();
}