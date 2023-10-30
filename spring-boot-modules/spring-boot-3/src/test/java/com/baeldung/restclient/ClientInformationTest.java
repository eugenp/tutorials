package com.baeldung.restclient;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.servlet.http.HttpServletRequest;

@SpringBootTest
public class ClientInformationTest {

    @Autowired
    private AccountsLogic accountsLogic;

    @Test
    void shouldGetMockHttpServletRequestWithHeadersAndReturnUserAgentInfo() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        // return sample user agent string when user-agent header accessed
        when(request.getHeader("user-agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36, acceptLanguage:en-US,en;q=0.9");

        Map<String, String> clientInfo = accountsLogic.getClientInfo(request);
        assertThat(clientInfo.get("os_family")).isEqualTo("Mac OS X");
        assertThat(clientInfo.get("device_family")).isEqualTo("Mac");
        assertThat(clientInfo.get("userAgent_family")).isEqualTo("Chrome");
    }
}
