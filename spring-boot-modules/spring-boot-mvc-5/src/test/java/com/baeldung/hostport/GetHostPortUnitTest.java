package com.baeldung.hostport;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetHostPortUnitTest {

    @Test
    void givenHttpWithDefaultPort_whenGetHostWithPort_thenReturnWithoutPort() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("example.com");
        Mockito.when(request.getServerPort()).thenReturn(80);

        String result = GetHostPort.getHostWithPort(request);

        assertEquals("http://example.com", result);
    }

    @Test
    void givenHttpsWithDefaultPort_whenGetHostWithPort_thenReturnWithoutPort() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("https");
        Mockito.when(request.getServerName()).thenReturn("secure.example.com");
        Mockito.when(request.getServerPort()).thenReturn(443);

        String result = GetHostPort.getHostWithPort(request);

        assertEquals("https://secure.example.com", result);
    }

    @Test
    void givenHttpWithCustomPort_whenGetHostWithPort_thenIncludePort() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("localhost");
        Mockito.when(request.getServerPort()).thenReturn(8080);

        String result = GetHostPort.getHostWithPort(request);

        assertEquals("http://localhost:8080", result);
    }

    @Test
    void givenHttpsWithCustomPort_whenGetHostWithPort_thenIncludePort() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("https");
        Mockito.when(request.getServerName()).thenReturn("test.example.com");
        Mockito.when(request.getServerPort()).thenReturn(8443);

        String result = GetHostPort.getHostWithPort(request);

        assertEquals("https://test.example.com:8443", result);
    }

    @Test
    void givenRequestURL_whenParse_thenExtractHostWithPort() throws MalformedURLException {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/api/test"));

        URL url = new URL(request.getRequestURL().toString());
        String hostWithPort = url.getPort() == -1
                ? String.format("%s://%s", url.getProtocol(), url.getHost())
                : String.format("%s://%s:%d", url.getProtocol(), url.getHost(), url.getPort());

        assertEquals("http://localhost:8080", hostWithPort);
    }

    @Test
    void givenForwardedHeaders_whenGetForwardedHost_thenReturnProxyHost() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getHeader("X-Forwarded-Host")).thenReturn("proxy.example.com");
        Mockito.when(request.getHeader("X-Forwarded-Proto")).thenReturn("https");
        Mockito.when(request.getHeader("X-Forwarded-Port")).thenReturn("443");

        String result = GetHostPort.getForwardedHost(request);

        assertEquals("https://proxy.example.com", result);
    }

    @Test
    void givenNoForwardedHeaders_whenGetForwardedHost_thenUseRequestValues() {
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getScheme()).thenReturn("http");
        Mockito.when(request.getServerName()).thenReturn("localhost");
        Mockito.when(request.getServerPort()).thenReturn(8080);

        String result = GetHostPort.getForwardedHost(request);

        assertEquals("http://localhost:8080", result);
    }
}
