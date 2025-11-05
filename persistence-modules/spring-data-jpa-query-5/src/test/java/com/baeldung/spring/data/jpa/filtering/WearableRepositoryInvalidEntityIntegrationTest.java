package com.baeldung.spring.data.jpa.filtering;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("h2")
@SpringBootTest(classes = FilteringApplication.class, properties = {
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:testdata.sql",
        "spring.jpa.hibernate.ddl-auto=none"
})
@EntityScan(basePackages = "com.baeldung.spring.data.jpa.filtering")
@ComponentScan(excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        value = WearableValidEntity.class
))
@Disabled("Disabled due to ApplicationContext failure caused by an invalid entity. Enable the test and view the logs.")
public class WearableRepositoryInvalidEntityIntegrationTest {

    @Autowired
    private WearableBrokenRepository wearableBrokenRepository;

    @Test
    public void testFindByCriteriaFailsWithIncorrectEntity() {
        assertThat(wearableBrokenRepository.findAllByOrderByPriceAscSensorTypeAscPopularityIndexDesc())
                .hasSize(4);
    }

}