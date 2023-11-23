package com.baeldung.logic;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ClientInformationUnitTest {

    @Test
    void givenMockHttpServletRequestWithHeaders_whenGetClientInfo_thenReturnsUserAGentInfo() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);

        // return sample user agent string when user-agent header accessed
        when(request.getHeader("user-agent")).thenReturn("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36, acceptLanguage:en-US,en;q=0.9");

        AccountLogic accountLogic = new AccountLogic();
        Map<String, String> clientInfo = accountLogic.getClientInfo(request);
        assertThat(clientInfo.get("os_family")).isEqualTo("Mac OS X");
        assertThat(clientInfo.get("device_family")).isEqualTo("Mac");
        assertThat(clientInfo.get("userAgent_family")).isEqualTo("Chrome");
    }
}
