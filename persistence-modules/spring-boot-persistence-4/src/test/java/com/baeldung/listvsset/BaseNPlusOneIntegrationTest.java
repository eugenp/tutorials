package com.baeldung.listvsset;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.listvsset.util.DatabaseUtil;
import com.baeldung.listvsset.util.JsonUtils;
import io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
  "spring.jpa.generate-ddl=true",
  "spring.jpa.show-sql=false",
  "spring.datasource.url=jdbc:h2:mem:~/test;DATABASE_TO_UPPER=false"
})
abstract public class BaseNPlusOneIntegrationTest<T> extends ParametrizationAware<T> {

    @Autowired
    protected List<Service<?>> services;
    @Autowired
    protected DatabaseUtil databaseUtil;
    @Autowired
    protected JsonUtils jsonUtils;

    private final Map<Class<?>, Service<?>> serviceMap = new HashMap<>();

    @PostConstruct
    void init() {
        for (Service<?> service : services) {
            Class<?> parametrization = service.getParametrizationClass().get(0);
            serviceMap.put(parametrization, service);
        }
    }

    @BeforeEach
    void setUp() {
        addUsers();
        SQLStatementCountValidator.reset();
         System.out.println("************************************************");
        System.out.println("\n\n\n\n\n");

    }

    @AfterEach
    void tearDown() {
        System.out.println("\n\n\n\n\n");
        System.out.println("************************************************");
        databaseUtil.truncateAllTables();
        SQLStatementCountValidator.reset();
    }

    @Test
    void givenCorrectConfigurationWhenStartContextThenRepositoryIsPresent() {
        assertThat(getUserService()).isNotNull();
    }

    @Test
    void givenCorrectDatabaseWhenStartThenDatabaseIsNotEmpty() {
        List<?> result = getUserService().findAll();
        assertThat(result).isNotEmpty();
    }

    protected Service<T> getUserService() {
        Class<T> parametrization = getParametrizationClass().get(0);
        return (Service<T>) serviceMap.get(parametrization);
    }

    protected abstract void addUsers();
}
