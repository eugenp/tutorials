package com.baeldung.batchreaderproperties.job;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import com.baeldung.batchreaderproperties.ContainsJobParameters;
import com.baeldung.batchreaderproperties.model.Medicine;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class MedicineProcessor implements ItemProcessor<Medicine, Medicine>, ContainsJobParameters {

    private ZonedDateTime triggeredDateTime;
    private String traceId;

    @Value("#{jobParameters['SALE_STARTS_DAYS']}")
    private long saleStartsDays;
    @Value("#{jobParameters['MEDICINE_SALE']}")
    private double medicineSale;

    @Override
    public Medicine process(Medicine medicine) {

        final Double originalPrice = medicine.getOriginalPrice();
        final Timestamp expirationDate = medicine.getExpirationDate();

        Duration daysToExpiration = Duration.between(ZonedDateTime.now(), ZonedDateTime.ofInstant(expirationDate.toInstant(), ZoneId.of("UTC")));

        if (daysToExpiration.toDays() < saleStartsDays) {
            medicine.setSalePrice(originalPrice * (1 - medicineSale));
            log.info("Trace = {}, calculated new sale price {} for medicine {}", traceId, medicine.getSalePrice(), medicine.getId());
        }

        return medicine;
    }
}
