package com.baeldung.batchreaderproperties.job;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import com.baeldung.batchreaderproperties.ContainsJobParameters;
import com.baeldung.batchreaderproperties.model.Medicine;
import com.baeldung.batchreaderproperties.model.MedicineCategory;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@RequiredArgsConstructor
@Slf4j
public class ExpiresSoonMedicineReader extends AbstractItemCountingItemStreamItemReader<Medicine> implements ContainsJobParameters {

    private static final String FIND_EXPIRING_SOON_MEDICINE = "Select * from MEDICINE where EXPIRATION_DATE >= CURRENT_DATE AND EXPIRATION_DATE <= DATEADD('DAY', ?, CURRENT_DATE)";
    //common job parameters populated in bean initialization
    private ZonedDateTime triggeredDateTime;
    private String traceId;
    //job parameter injected by Spring
    @Value("#{jobParameters['DEFAULT_EXPIRATION']}")
    private long defaultExpiration;

    private final JdbcTemplate jdbcTemplate;

    private List<Medicine> expiringMedicineList;

    @Override
    protected Medicine doRead() {
        if (expiringMedicineList != null && !expiringMedicineList.isEmpty()) {
            return expiringMedicineList.get(getCurrentItemCount() - 1);
        }

        return null;
    }

    @Override
    protected void doOpen() {
        expiringMedicineList = jdbcTemplate.query(FIND_EXPIRING_SOON_MEDICINE, ps -> ps.setLong(1, defaultExpiration), (rs, row) -> getMedicine(rs));

        log.info("Trace = {}. Found {} meds that expires soon", traceId, expiringMedicineList.size());
        if (!expiringMedicineList.isEmpty()) {
            setMaxItemCount(expiringMedicineList.size());
        }
    }

    private static Medicine getMedicine(ResultSet rs) throws SQLException {
        return new Medicine(UUID.fromString(rs.getString(1)), rs.getString(2), MedicineCategory.valueOf(rs.getString(3)), rs.getTimestamp(4), rs.getDouble(5), rs.getObject(6, Double.class));
    }

    @Override
    protected void doClose() {

    }

    @PostConstruct
    public void init() {
        setName(ClassUtils.getShortName(getClass()));
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        JobParameters parameters = stepExecution.getJobExecution()
            .getJobParameters();
        log.info("Before step params: {}", parameters);
    }
}
