# Getting scheduled job's Cron Value from Database in Spring Boot

## 1. Overview

In this article, we explore the `cronfromdb` sample application under `src/main/java/com/baeldung/cronfromdb`. The application entry point is `CronFromDbApplication.java`.

Our goal is to store a scheduled job's cron expression in the database rather than hardcoding it in the application.

We will cover two approaches:

1. loading the cron value once through a `cronLoader` bean and using it from `@Scheduled`
2. re-reading the cron value from the database on every scheduling decision through `SchedulingConfigurer`

Throughout the article, we will use the provided classes and SQL scripts as working examples.

## 2. Introduction to the problem

When we work with Spring scheduling, the most straightforward option is usually to place the cron expression directly in `@Scheduled`:

```java
@Scheduled(cron = "*/5 * * * * ?")
public void run() {
    // job logic
}
```

This works well, but it also hardcodes the schedule into the application.

The problem appears when we want to fetch the cron expression from a database. We cannot simply write something like this:

```java
private String cronExpression = loadFromDatabase();

@Scheduled(cron = cronExpression)
public void run() {
    // invalid approach
}
```

The reason is that the `cron` attribute in an annotation cannot be populated from an arbitrary runtime variable. In practice, we either need an indirection that Spring can resolve for us, or we need to move away from annotation-driven scheduling altogether.

In this sample, we demonstrate both options:

- using a bean reference with `@Scheduled(cron = "#{@cronLoader}")`
- using `SchedulingConfigurer` and a `Trigger` that reads the database every time the next execution is calculated

## 3. Prepare a database

For this demo, we use a small H2 in-memory database. The application already includes the required dependencies for Spring Web, Spring Data JPA, and H2 in `pom.xml`.

### 3.1. SQL initialization

The application is configured to initialize SQL scripts at startup:

```properties
spring.sql.init.mode=always
spring.jpa.hibernate.ddl-auto=none
```

As a result, Spring Boot runs `schema.sql` and `data.sql` when the application starts.

### 3.2. Create a simple table

The table is intentionally minimal. We only need an identifier and the cron expression itself.

```sql
CREATE TABLE IF NOT EXISTS cron_config (
    id BIGINT PRIMARY KEY,
    cron_expression VARCHAR(255) NOT NULL
);
```

We also insert one row so the application has a cron value as soon as it starts:

```sql
INSERT INTO cron_config (id, cron_expression) VALUES (1, '*/5 * * * * ?');
```

So by default, the schedule runs every 5 seconds.

### 3.3. Map the table with an entity and repository

The JPA entity is equally small:

```java
package com.baeldung.cronfromdb.crondata;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cron_config")
public class CronEntity {

    @Id
    private Long id;

    private String cronExpression;

    public CronEntity() {
    }

    public CronEntity(Long id, String cronExpression) {
        this.id = id;
        this.cronExpression = cronExpression;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
}
```

The repository is just as simple and gives us the CRUD operations we need:

```java
package com.baeldung.cronfromdb.crondata;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CronConfigRepository extends JpaRepository<CronEntity, Long> {
}
```

With these pieces in place, we can read and update cron values from the database.

## 4. Using a CronLoader Bean

Our first approach keeps `@Scheduled`, but avoids placing the cron expression directly in the annotation.

### 4.1. Define a bean that loads the cron value

We start with a small configuration class that defines a bean named `cronLoader`:

```java
package com.baeldung.cronfromdb.scheduling;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;

@Configuration
public class CronLoaderConfig {

    @Bean
    String cronLoader(CronConfigRepository repository) {
        return repository.findById(1L)
            .map(CronEntity::getCronExpression)
            .orElseThrow(() -> new RuntimeException("Cron expression not found in DB"));
    }
}
```

This bean reads the cron expression from the row with `id = 1` and returns it as a `String`.

### 4.2. Use the bean from `@Scheduled`

Next, we can reference that bean through SpEL in our scheduled job:

```java
package com.baeldung.cronfromdb.scheduling;

import static java.time.LocalTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AnnotationScheduledJob {

    private static final Logger log = LoggerFactory.getLogger(AnnotationScheduledJob.class);

    @Scheduled(cron = "#{@cronLoader}")
    public void run() {
        log.info("✅ [{}] Job executed - cron loaded from DB via @Scheduled", now());
    }
}
```

This is the key idea: we still use `@Scheduled`, but the cron expression comes from the `cronLoader` bean instead of being embedded directly in the annotation.

### 4.3. Start the application and observe the console output

The application entry point is the usual Spring Boot bootstrap class:

```java
package com.baeldung.cronfromdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CronFromDbApplication {

    public static void main(String[] args) {
        SpringApplication.run(CronFromDbApplication.class, args);
    }
}
```

When we start the application, Spring initializes the database, creates the `cronLoader` bean, and then schedules `AnnotationScheduledJob` by using the value loaded from the database.

From the project root, we can start the sample with:

```bash
mvn spring-boot:run
```

Because `data.sql` inserts `*/5 * * * * ?`, we should see the annotation-based job log roughly every five seconds:

```text
... AnnotationScheduledJob : ✅ [10:00:05.002] Job executed - cron loaded from DB via @Scheduled
... AnnotationScheduledJob : ✅ [10:00:10.001] Job executed - cron loaded from DB via @Scheduled
... AnnotationScheduledJob : ✅ [10:00:15.000] Job executed - cron loaded from DB via @Scheduled
```

This gives us a straightforward way to verify that the cron value is being loaded from the database.

### 4.4. Limitation of the `cronLoader` approach

This approach is convenient, but it comes with an important limitation.

Spring loads the cron value when it creates the `cronLoader` bean during application startup. After that, the scheduled method continues using the resolved value.

If we update the `cron_config` table later, the schedule does **not** automatically change for the running application. In practice, we would typically need to restart the application to pick up the new value.

So, this approach is a good fit only when loading the schedule once at startup is acceptable.

## 4. Using SchedulingConfigurer

If we want the application to react to database changes without restarting, we need a more dynamic solution.

### 4.1. Implementing SchedulingConfigurer

Instead of relying on `@Scheduled`, we can implement `SchedulingConfigurer` and register a trigger task ourselves.

The sample does that in `DynamicScheduledConfig`:

```java
package com.baeldung.cronfromdb.scheduling;

import static java.time.LocalTime.now;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;

@Configuration
public class DynamicScheduledConfig implements SchedulingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DynamicScheduledConfig.class);

    private final CronConfigRepository repository;

    public DynamicScheduledConfig(CronConfigRepository repository) {
        this.repository = repository;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> log.info("✅ [{}] DynamicScheduledConfig executed - cron re-read from DB per execution", now()), triggerContext -> {
            String cronExpression = repository.findById(1L)
                .map(CronEntity::getCronExpression)
                .orElseThrow(() -> new RuntimeException("Cron expression not found in DB"));
            return new CronTrigger(cronExpression).nextExecution(triggerContext);
        });
    }
}
```

Here is what happens in this implementation:

1. we register a task with `addTriggerTask(...)`
2. the runnable contains the work we want to execute
3. the trigger callback reads the latest cron expression from the database
4. a new `CronTrigger` is created from that value
5. Spring asks that trigger for the next execution time

This is the main difference from the `cronLoader` bean approach: the cron expression is re-read from the database whenever Spring calculates the next execution time.

For easier testing, the sample also includes a controller that updates the database record:

```java
package com.baeldung.cronfromdb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.cronfromdb.crondata.CronConfigRepository;
import com.baeldung.cronfromdb.crondata.CronEntity;

@RestController
public class CronController {

    private static final Logger log = LoggerFactory.getLogger(CronController.class);
    private final CronConfigRepository repository;

    public CronController(CronConfigRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cron")
    public String updateCron() {
        CronEntity cronEntity = repository.findById(1L)
            .orElseThrow(() -> new RuntimeException("Cron expression not found in database"));
        cronEntity.setCronExpression("*/10 * * * * ?");
        repository.save(cronEntity);
        String msg = "[DB] ⏰ Updated cron expression in DB to: */10 * * * * ?";
        log.info(msg);
        return msg;
    }
}
```

This endpoint lets us change the cron from every 5 seconds to every 10 seconds without updating the database manually.

### 4.2. Demonstration

To make the logs easier to read during this demo, we can temporarily comment out the annotation-based scheduling line in `AnnotationScheduledJob` so that only the dynamic task is running:

```java
@Component
public class AnnotationScheduledJob {

    private static final Logger log = LoggerFactory.getLogger(AnnotationScheduledJob.class);

    // @Scheduled(cron = "#{@cronLoader}")
    public void run() {
        log.info("✅ [{}] Job executed - cron loaded from DB via @Scheduled", now());
    }
}
```

Then we start the application again.

```bash
mvn spring-boot:run
```

Because `data.sql` still seeds `*/5 * * * * ?`, the dynamic task initially runs about every 5 seconds. We should see log entries similar to these:

```text
... DynamicScheduledConfig : ✅ [10:05:05.001] DynamicScheduledConfig executed - cron re-read from DB per execution
... DynamicScheduledConfig : ✅ [10:05:10.001] DynamicScheduledConfig executed - cron re-read from DB per execution
... DynamicScheduledConfig : ✅ [10:05:15.001] DynamicScheduledConfig executed - cron re-read from DB per execution
```

Next, we call the update endpoint:

```bash
curl http://localhost:8080/cron
```

The controller updates the stored value to `*/10 * * * * ?` and logs the change:

```text
... CronController : [DB] ⏰ Updated cron expression in DB to: */10 * * * * ?
```

After that, the dynamic task should start following the new interval. In other words, instead of firing every five seconds, subsequent executions should move to a ten-second rhythm:

```text
... DynamicScheduledConfig : ✅ [10:05:20.000] DynamicScheduledConfig executed - cron re-read from DB per execution
... DynamicScheduledConfig : ✅ [10:05:30.000] DynamicScheduledConfig executed - cron re-read from DB per execution
... DynamicScheduledConfig : ✅ [10:05:40.000] DynamicScheduledConfig executed - cron re-read from DB per execution
```

This demonstrates the main benefit of `SchedulingConfigurer`: we can change the cron expression in the database and let the running application adapt without a restart.

## 5. Conclusion

In this article, we explored two ways to get a scheduled job's cron value from a database in Spring Boot.

With the `cronLoader` bean approach, we keep the implementation simple and continue using `@Scheduled`, but we only load the cron value once during startup.

With `SchedulingConfigurer`, we take control of task registration and re-read the cron expression from the database whenever Spring calculates the next execution time. This makes the schedule dynamic and allows runtime updates.

So if we only need a startup-time value, a bean-backed `@Scheduled` setup is often enough. But if we want truly dynamic scheduling, `SchedulingConfigurer` is the better choice.