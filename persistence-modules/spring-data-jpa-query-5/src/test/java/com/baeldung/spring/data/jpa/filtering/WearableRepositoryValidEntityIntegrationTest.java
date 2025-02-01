package com.baeldung.spring.data.jpa.filtering;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("h2")
@SpringBootTest(classes = FilteringApplication.class, properties = {
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:testdata.sql",
        "spring.jpa.hibernate.ddl-auto=none"
})
@EntityScan(basePackages = "com.baeldung.spring.data.jpa.filtering")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = {WearableInvalidEntity.class}))
@EnableJpaRepositories(
        basePackageClasses = WearableRepository.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = WearableBrokenRepository.class)
)
public class WearableRepositoryValidEntityIntegrationTest {

    @Autowired
    private WearableRepository wearableRepository;

    @Test
    public void fetchWearablesListUsingCustomQueryThenAllOfThemPresent() {
        List<WearableValidEntity> wearables = wearableRepository.findAllByOrderByPriceAscSensorTypeAscPopularityIndexDesc();
        assertThat(wearables).hasSize(4);

        assertEntityFields(wearables.get(0), "SensaTag", "120.00", "Proximity", 2);
        assertEntityFields(wearables.get(1), "SensaShirt", "150.00", "Human Activity Recognition", 2);
        assertEntityFields(wearables.get(2), "SensaBelt", "300.00", "Heart Rate", 3);
        assertEntityFields(wearables.get(3), "SensaWatch", "500.00", "Accelerometer", 5);
    }

    private static void assertEntityFields(
            WearableValidEntity wearable,
            String name,
            String price,
            String sensorType,
            Integer popularityIndex
    ) {
        assertThat(wearable.getSensorType()).isEqualTo(sensorType);
        assertThat(wearable.getName()).isEqualTo(name);
        assertThat(wearable.getPrice()).isEqualTo(price);
        assertThat(wearable.getPopularityIndex()).isEqualTo(popularityIndex);
    }


}