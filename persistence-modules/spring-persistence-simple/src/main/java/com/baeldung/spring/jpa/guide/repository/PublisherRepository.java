package com.baeldung.spring.jpa.guide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baeldung.spring.jpa.guide.model.Publishers;

public interface PublisherRepository extends JpaRepository<Publishers, Integer> {

    List<Publishers> findAllByLocation(String location);

    @Query("SELECT p FROM Publishers p WHERE p.journals > :minJournals AND p.location = :location")
    List<Publishers> findPublishersWithMinJournalsInLocation(int minJournals, String location);
}
