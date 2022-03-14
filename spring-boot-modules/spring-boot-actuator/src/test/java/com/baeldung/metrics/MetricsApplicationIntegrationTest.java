package com.baeldung.metrics;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(
  classes = MetricsApplication.class,
  webEnvironment = RANDOM_PORT,
  properties = {"fixedDelay.in.milliseconds=2000"}
)
@ActiveProfiles("metrics")
class MetricsApplicationIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenStatuses_WhenScheduledMethodExecuted_ExpectCountsAreAggregated() {
        restTemplate.getForObject("/metrics/metric/notFound", String.class);
        restTemplate.getForObject("/metrics/metric", String.class);

        await().untilAsserted(() -> {
            Object[][] statusCounts = restTemplate.getForObject("/metrics/metric-graph-data", Object[][].class);

            assertThat(statusCounts[0]).contains("counter.status.200", "counter.status.404");

            List<Integer> requestCounts = getRequestCounts(statusCounts);
            verify404RequestFrom(requestCounts);
            verify200RequestsFrom(requestCounts);
        });
    }

    private static void verify200RequestsFrom(List<Integer> requestCounts) {
        assertThat(requestCounts.size()).isGreaterThan(1);
    }

    private static void verify404RequestFrom(List<Integer> requestCounts) {
        assertThat(requestCounts).contains(1);
    }

    private static List<Integer> getRequestCounts(Object[][] statusCounts) {
        List<Integer> requestCounts = new ArrayList<>();
        for (int i = 1; i < statusCounts.length; i++) {
            for (int j = 1; j < statusCounts[i].length; j++) {
                Integer count = (Integer) statusCounts[i][j];
                if (count >= 1) {
                    requestCounts.add(count);
                }
            }
        }
        return requestCounts;
    }

}
