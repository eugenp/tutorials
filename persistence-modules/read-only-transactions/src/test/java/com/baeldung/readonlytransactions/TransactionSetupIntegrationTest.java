package com.baeldung.readonlytransactions;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.readonlytransactions.mysql.dao.MyRepoJPA;
import com.baeldung.readonlytransactions.mysql.dao.MyRepoJdbc;
import com.baeldung.readonlytransactions.mysql.dao.MyRepoSpring;
import com.baeldung.readonlytransactions.mysql.spring.Config;
import com.baeldung.readonlytransactions.mysql.spring.ReadOnlyInterception;
import com.baeldung.readonlytransactions.mysql.spring.entities.BookEntity;
import com.baeldung.readonlytransactions.mysql.spring.repositories.BookRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

// Needs to be run with Docker look at the readme file.
@Disabled
@ExtendWith(SpringExtension.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, initializers = TransactionSetupIntegrationTest.TestConfig.class, classes = { ReadOnlyInterception.class })
class TransactionSetupIntegrationTest {

    static class TestConfig implements ApplicationContextInitializer<GenericApplicationContext> {
        @Override
        public void initialize(GenericApplicationContext applicationContext) {
            new AnnotatedBeanDefinitionReader(applicationContext).register(Config.class);
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(TransactionSetupIntegrationTest.class);

    @Autowired
    private MyRepoSpring repoSpring;

    @Autowired
    private BookRepository repository;

    @Test
    void givenTheDifferentTransactionSetup_whenRunningAThroughputTest_thenWeCanObserveTheSystem() {
        Map<String, Supplier<Long>> jdbcConfigurations = new LinkedHashMap<>();

        jdbcConfigurations.put("JPA: Session read only true and autocommit disabled", () -> new MyRepoJPA().runQuery());

        jdbcConfigurations.put("Spring: Session read only and autocommit true", () -> repoSpring.runQuery());

        jdbcConfigurations.put("JDBC: Global read only and autocommit enabled", () -> new MyRepoJdbc(true, true).runQuery(null, null));
        jdbcConfigurations.put("JDBC: Global read only false and autocommit enabled", () -> new MyRepoJdbc(false, true).runQuery(null, null));
        jdbcConfigurations.put("JDBC: Global read only true and autocommit disabled", () -> new MyRepoJdbc(true, false).runQuery(null, null));

        jdbcConfigurations.put("JDBC: Session read only and autocommit disabled", () -> new MyRepoJdbc(false, false).runQuery(false, false));
        jdbcConfigurations.put("JDBC: Session read only and autocommit enabled", () -> new MyRepoJdbc(false, false).runQuery(true, true));
        jdbcConfigurations.put("JDBC: Session read only false and autocommit enabled", () -> new MyRepoJdbc(false, false).runQuery(false, true));
        jdbcConfigurations.put("JDBC: Session read only true and autocommit disabled", () -> new MyRepoJdbc(false, false).runQuery(true, false));

        jdbcConfigurations.entrySet()
            .stream()
            .flatMap(entry -> {
                Stream.Builder<String> builder = Stream.builder();
                return builder.add(entry.getKey() + " Total: " + entry.getValue()
                        .get())
                    .add(entry.getKey() + " Total: " + entry.getValue()
                        .get())
                    .add(entry.getKey() + " Total: " + entry.getValue()
                        .get())
                    .build();
            })
            .collect(toList())
            .stream()
            .peek(o -> logger.info("--------------------------------------------------"))
            .forEach(logger::info);
    }

    @Test
    void givenThatSpringTransactionManagementIsEnabled_whenAMethodIsAnnotatedAsTransactionalReadOnly_thenSpringShouldTakeCareOfTheTransaction() {
        Long id = repository.get(2L);

        assertNotNull(id);
    }

    @Test
    void givenThatSpringTransactionManagementIsEnabled_whenAMethodIsAnnotatedAsTransactional_thenSpringShouldTakeCareOfTheTransaction() {
        BookEntity book = new BookEntity();
        book.setName("Persistence test");
        book.setUuid(UUID.randomUUID()
            .toString());
        book = repository.persist(book);

        assertNotNull(book.getId());
    }
}