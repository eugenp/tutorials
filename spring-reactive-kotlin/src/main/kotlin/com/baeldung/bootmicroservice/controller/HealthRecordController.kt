package com.baeldung.bootmicroservice.controller

import com.baeldung.bootmicroservice.model.AverageHealthStatus
import com.baeldung.bootmicroservice.model.HealthRecord
import com.baeldung.bootmicroservice.repository.HealthRecordRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HealthRecordController(val repository: HealthRecordRepository) {

    @PostMapping("/health/{profileId}/record")
    fun storeHealthRecord(@PathVariable("profileId") profileId: Long, @RequestBody record: HealthRecord): Mono<HealthRecord> =
            repository.save(HealthRecord(null
                    , profileId
                    , record.temperature
                    , record.bloodPressure
                    , record.heartRate
                    , record.date))

    @GetMapping("/health/{profileId}/avg")
    fun fetchHealthRecordAverage(@PathVariable("profileId") profileId: Long): Mono<AverageHealthStatus> =
            repository.findByProfileId(profileId)
                    .reduce(
                            AverageHealthStatus(0, 0.0, 0.0, 0.0)
                            , { s, r ->
                        AverageHealthStatus(s.cnt + 1
                                , s.temperature + r.temperature
                                , s.bloodPressure + r.bloodPressure
                                , s.heartRate + r.heartRate
                        )
                    }
                    ).map { s ->
                        AverageHealthStatus(s.cnt
                                , if (s.cnt != 0) s.temperature / s.cnt else 0.0
                                , if (s.cnt != 0) s.bloodPressure / s.cnt else 0.0
                                , if (s.cnt != 0) s.heartRate / s.cnt else 0.0)
                    }

}