package com.baeldung.formparamdoc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class SubscriptionServiceUnitTest {

    private final SubscriptionService service = new SubscriptionService();

    @Test
    void whenSubscribeTwice_thenIdsIncrease() {
        SubscriptionForm first = form("weekly", Arrays.asList("spring"));
        SubscriptionForm second = form("monthly", Arrays.asList("java"));

        SubscriptionResponse firstResponse = service.subscribe(first);
        SubscriptionResponse secondResponse = service.subscribe(second);

        assertThat(firstResponse.id()).isEqualTo(1001L);
        assertThat(secondResponse.id()).isEqualTo(1002L);
    }

    @Test
    void whenTopicsAreMissing_thenResponseContainsEmptyList() {
        SubscriptionForm form = form("weekly", null);

        SubscriptionResponse response = service.subscribe(form);

        assertThat(response.topics()).isEmpty();
        assertThat(response.marketingAccepted()).isTrue();
    }

    private SubscriptionForm form(String frequency, List<String> topics) {
        SubscriptionForm form = new SubscriptionForm();
        form.setEmail("api@baeldung.com");
        form.setName("Baeldung API");
        form.setFrequency(frequency);
        form.setTopics(topics);
        form.setMarketingAccepted(true);
        return form;
    }
}
