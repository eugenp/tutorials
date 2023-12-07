package com.baeldung.batch_reader_properties.job;

import java.time.ZonedDateTime;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;

import com.baeldung.batch_reader_properties.ContainsJobParameters;
import com.baeldung.batch_reader_properties.model.Medicine;

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
