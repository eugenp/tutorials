package com.baeldung.bootmicroservice.repository

import com.baeldung.bootmicroservice.model.HealthRecord
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface HealthRecordRepository: ReactiveCrudRepository<HealthRecord, Long> {
    @Query("select p.* from health_record p where p.profile_id = :profileId ")
    fun findByProfileId(profileId: Long): Flux<HealthRecord>
}