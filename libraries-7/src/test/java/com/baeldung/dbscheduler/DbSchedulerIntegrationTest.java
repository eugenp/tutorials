package com.baeldung.dbscheduler;

import javax.sql.DataSource;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.kagkarlsson.scheduler.Scheduler;
import com.github.kagkarlsson.scheduler.task.Task;
import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.CronSchedule;
import com.github.kagkarlsson.scheduler.task.schedule.FixedDelay;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DbSchedulerIntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(DbSchedulerIntegrationTest.class);

    private DataSource dataSource;

    @BeforeEach
    void setUp() throws Exception {
        JDBCDataSource ds = new JDBCDataSource();
        ds.setURL("jdbc:hsqldb:mem:testdb");
        ds.setUser("SA");
        ds.setPassword("");
        dataSource = ds;

        try (Connection conn = dataSource.getConnection()) {
            runScript(conn, "dbscheduler.sql");
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        try (Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute("SHUTDOWN");
        }
    }

    private void runScript(Connection connection, String resourcePath) throws Exception {
        Path path = Path.of(getClass().getClassLoader().getResource(resourcePath).toURI());
        String script = Files.readString(path);

        try (Statement stmt = connection.createStatement()) {
            for (String sql : script.split(";")) {
                String trimmed = sql.trim();
                if (!trimmed.isEmpty()) {
                    stmt.execute(trimmed);
                }
            }
        }
    }

    @Test
    void whenStartingTheScheduler_thenTheSchedulerIsRunning() throws Exception {
        Scheduler scheduler = Scheduler.create(dataSource)
            .startTasks()
            .registerShutdownHook()
            .build();

        scheduler.start();
        TimeUnit.SECONDS.sleep(1);

        assertTrue(scheduler.getSchedulerState().isStarted());

        scheduler.stop();
        assertTrue(scheduler.getSchedulerState().isShuttingDown());
    }

    @Test
    void whenStartingARecurringTask_thenTheTaskRunsRegularly() throws Exception {
        CountDownLatch countDownLatch  = new CountDownLatch(3);
        AtomicInteger counter = new AtomicInteger(0);

        RecurringTask<Void> task = Tasks.recurring("my-task", FixedDelay.ofSeconds(5))
            .execute((instance, context) -> {
                LOG.info("Executed!");
                countDownLatch.countDown();
                counter.incrementAndGet();
            });

        Scheduler scheduler = Scheduler.create(dataSource)
            .startTasks(task)
            .registerShutdownHook()
            .pollingInterval(Duration.ofSeconds(2))
            .build();

        scheduler.start();

        countDownLatch.await();

        assertEquals(3, counter.get());
        scheduler.stop();
    }

    @Test
    void whenStartingAOneTimeTask_thenTheTaskRunsOnce() throws Exception {
        AtomicInteger counter = new AtomicInteger(0);

        TaskDescriptor<String> taskDescriptor = TaskDescriptor.of("my-onetime-task", String.class);

        Task<String> task = Tasks.oneTime(taskDescriptor)
            .execute((inst, ctx) -> {
                LOG.info("Executed! Custom data {}, Instance {}", inst.getData(), inst.getId());
                counter.incrementAndGet();
            });
        Scheduler scheduler = Scheduler.create(dataSource, task)
            .pollingInterval(Duration.ofSeconds(2))
            .build();

        scheduler.start();

        scheduler.schedule(taskDescriptor.instance(UUID.randomUUID().toString())
            .data("Hello")
            .scheduledTo(Instant.now().plusSeconds(5))
        );

        TimeUnit.SECONDS.sleep(20);
        assertEquals(1, counter.get());

        scheduler.stop();
    }

    @Test
    void whenStartingADynamicRecurringTask_thenTheTaskRunsAsExpected() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        AtomicInteger counter = new AtomicInteger(0);

        TaskDescriptor<String> taskDescriptor = TaskDescriptor.of("my-dynamic-recurring-task", String.class);

        Task<String> task = Tasks.recurring(taskDescriptor, new CronSchedule("*/5 * * * * ?", ZoneId.of("UTC")))
            .execute((inst, ctx) -> {
                LOG.info("Executed! Custom data {}, Instance {}", inst.getData(), inst.getId());
                countDownLatch.countDown();
                counter.incrementAndGet();
            });
        Scheduler scheduler = Scheduler.create(dataSource, task)
            .pollingInterval(Duration.ofSeconds(1))
            .build();

        scheduler.start();

        scheduler.schedule(taskDescriptor.instance(UUID.randomUUID().toString())
            .data("Hello")
            .scheduledTo(Instant.now().plusSeconds(10))
        );

        countDownLatch.await();

        assertEquals(3, counter.get());
        scheduler.stop();
    }
}
