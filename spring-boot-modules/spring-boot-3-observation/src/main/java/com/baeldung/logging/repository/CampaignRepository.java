package com.baeldung.logging.repository;

import com.baeldung.logging.model.Campaign;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    @Query("SELECT c FROM Campaign c WHERE c.startDate BETWEEN :startDate AND :endDate")
    List<Campaign> findCampaignsByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
