package com.baeldung.batchreaderproperties.job;

import java.time.ZonedDateTime;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.baeldung.batchreaderproperties.ContainsJobParameters;
import com.baeldung.batchreaderproperties.model.Medicine;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class MedicineWriter implements ItemWriter<Medicine>, ContainsJobParameters {

    private ZonedDateTime triggeredDateTime;
    private String traceId;

    @Override
    public void write(Chunk<? extends Medicine> chunk) {
        chunk.forEach((medicine) -> log.info("Trace = {}. This medicine is expiring {}", traceId, medicine));

        log.info("Finishing job started at {}", triggeredDateTime);
    }
}
