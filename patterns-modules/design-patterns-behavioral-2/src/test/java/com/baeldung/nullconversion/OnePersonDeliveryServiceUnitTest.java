package com.baeldung.nullconversion;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.baeldung.nullconversion.service.OnePersonExplicitDeliveryService;
import com.baeldung.nullconversion.service.OnePersonGuavaOptionalDeliveryService;
import com.baeldung.nullconversion.service.OnePersonOptionalDeliveryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class OnePersonDeliveryServiceUnitTest {

    @ParameterizedTest
    @ArgumentsSource(PersonProvider.class)
    void givenMockDeliverServiceWhenNullValuesThenExplicitServiceHandleThem(Person person, Delivery expected) {
        DeliveryService deliveryService = new OnePersonExplicitDeliveryService(person);
        Delivery actual = deliveryService.calculateDeliveryForPerson(1L);
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ArgumentsSource(PersonProvider.class)
    void givenMockDeliverServiceWhenNullValuesThenOptionalServiceHandleThem(Person person, Delivery expected) {
        DeliveryService deliveryService = new OnePersonOptionalDeliveryService(person);
        Delivery actual = deliveryService.calculateDeliveryForPerson(1L);
        assertThat(actual).isEqualTo(expected);
    }
}