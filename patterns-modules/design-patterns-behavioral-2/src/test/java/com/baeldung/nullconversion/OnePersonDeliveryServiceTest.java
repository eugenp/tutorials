package com.baeldung.nullconversion;


import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.nullconversion.service.OnePersonExplicitDeliveryService;
import com.baeldung.nullconversion.service.OnePersonOptionalDeliveryService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class OnePersonDeliveryServiceTest {

    @ParameterizedTest
    @ArgumentsSource(PersonProvider.class)
    void calculateDeliveryForPerson(Person person, Delivery expected) {
        DeliveryService deliveryService = new OnePersonExplicitDeliveryService(person);
        Delivery actual = deliveryService.calculateDeliveryForPerson(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ArgumentsSource(PersonProvider.class)
    void calculateDeliveryForPersonWithOptional(Person person, Delivery expected) {
        DeliveryService deliveryService = new OnePersonOptionalDeliveryService(person);
        Delivery actual = deliveryService.calculateDeliveryForPerson(1L);
        assertThat(actual).isEqualTo(expected);
    }
}