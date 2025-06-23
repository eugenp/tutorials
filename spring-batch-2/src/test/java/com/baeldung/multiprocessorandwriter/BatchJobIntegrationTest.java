package com.baeldung.multiprocessorandwriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import com.baeldung.multiprocessorandwriter.config.BatchConfig;

import com.baeldung.multiprocessorandwriter.model.Customer;
import com.baeldung.multiprocessorandwriter.processor.CustomerProcessorRouter;

@SpringBootTest
@EnableAutoConfiguration
@ContextConfiguration(classes = { BatchConfig.class, JpaTestConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(BatchJobIntegrationTest.TestConfig.class)
public class BatchJobIntegrationTest {


    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    @TestConfiguration
    static class TestConfig {
        @Autowired
        private JobLauncher jobLauncher;

        @Autowired
        private Job job;

        @Bean
        public JobLauncherTestUtils jobLauncherTestUtils() {
            JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
            jobLauncherTestUtils.setJobLauncher(jobLauncher);
            jobLauncherTestUtils.setJob(job);
            return jobLauncherTestUtils;
        }
    }
    @Autowired
    private DataSource dataSource;

    private Path outputFile;

    @BeforeEach
    public void setup() throws IOException {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource("org/springframework/batch/core/schema-h2.sql"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        outputFile = Paths.get("output/processed_customers.txt");
        Files.deleteIfExists(outputFile);  // clean output file before test
    }

    @Test
    public void givenTypeA_whenProcess_thenNameIsUppercaseAndEmailPrefixedWithA() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Customer> dbCustomers = jdbcTemplate.query(
            "SELECT id, name, email, type FROM customer WHERE type = 'A'",
            (rs, rowNum) -> new Customer(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("type"))
        );

        assertFalse(dbCustomers.isEmpty());

        dbCustomers.forEach(c -> {
            assertEquals(c.getName(), c.getName().toUpperCase());
            assertTrue(c.getEmail().startsWith("A_"));
        });
    }

    @Test
    public void givenTypeB_whenProcess_thenNameIsLowercaseAndEmailPrefixedWithB() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        assertTrue(Files.exists(outputFile));
        List<String> lines = Files.readAllLines(outputFile);

        boolean hasTypeB = lines.stream().anyMatch(line -> line.endsWith(",B"));
        assertTrue(hasTypeB);

        lines.forEach(line -> {
            String[] parts = line.split(",");
            if ("B".equals(parts[3])) {
                assertEquals(parts[1], parts[1].toLowerCase());
                assertTrue(parts[2].startsWith("B_"));
            }
        });
    }
}