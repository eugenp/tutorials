package com.baeldung.clientinfo;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jakarta.servlet.http.HttpServletRequest;

public class ClientInformationUnitTest {

    @Test
    void givenMockHttpServletRequestWithHeaders_whenGetClientInfo_thenReturnsUserAGentInfo() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        when(request.getHeader("user-agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36, acceptLanguage:en-US,en;q=0.9");
        when(request.getHeader("content-type")).thenReturn("application/json");
        when(request.getRemoteAddr()).thenReturn("198.167.0.1");
        when(request.getRemoteHost()).thenReturn("baeldung.com");
        when(request.getRemoteUser()).thenReturn("baeldung");

        AccountLogic accountLogic = new AccountLogic();
        Map<String, String> clientInfo = accountLogic.getClientInfo(request);
        assertThat(clientInfo.get("os_family")).isEqualTo("Mac OS X");
        assertThat(clientInfo.get("device_family")).isEqualTo("Mac");
        assertThat(clientInfo.get("userAgent_family")).isEqualTo("Chrome");
        assertThat(clientInfo.get("content_type")).isEqualTo("application/json");
        assertThat(clientInfo.get("remote_user")).isEqualTo("baeldung");
        assertThat(clientInfo.get("remote_address")).isEqualTo("198.167.0.1");
        assertThat(clientInfo.get("remote_host")).isEqualTo("baeldung.com");
    }
}