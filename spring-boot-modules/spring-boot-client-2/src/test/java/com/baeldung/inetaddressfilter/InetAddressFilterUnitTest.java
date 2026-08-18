package com.baeldung.inetaddressfilter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.http.client.FilteredHostException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InetAddressFilterUnitTest {

    @LocalServerPort
    int port;

    @Autowired
    private RestClient restClient;

    @Test
    void whenCallingLoopbackAddress_thenFilteredHostExceptionIsThrown() {

        assertThatThrownBy(() ->
            restClient.get()
              .uri("https://127.0.0.1:8080")
              .retrieve()
              .toBodilessEntity()
        )
            .isInstanceOf(FilteredHostException.class)
            .hasMessageContaining("127.0.0.1");
    }
}