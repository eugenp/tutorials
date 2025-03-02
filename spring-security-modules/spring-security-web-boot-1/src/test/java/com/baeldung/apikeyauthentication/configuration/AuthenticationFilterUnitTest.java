package com.baeldung.apikeyauthentication.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterUnitTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthenticationFilter authenticationFilter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenValidAuthentication_whenDoFilter_thenProceedsWithFilterChain() throws IOException, ServletException {
        try (MockedStatic<AuthenticationService> mockedAuthService = Mockito.mockStatic(AuthenticationService.class)) {
            mockedAuthService.when(() -> AuthenticationService.getAuthentication(request)).thenReturn(authentication);
            
            authenticationFilter.doFilter(request, response, filterChain);
            
            mockedAuthService.verify(() -> AuthenticationService.getAuthentication(request));
            verify(filterChain).doFilter(request, response);
            verify(response, never()).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Test
    public void givenAuthenticationFailure_whenDoFilter_thenReturnsUnauthorizedResponse() throws IOException, ServletException {
        try (MockedStatic<AuthenticationService> mockedAuthService = Mockito.mockStatic(AuthenticationService.class)) {
            mockedAuthService.when(() -> AuthenticationService.getAuthentication(request))
                .thenThrow(new RuntimeException("Authentication failed"));
            
            PrintWriter writer = mock(PrintWriter.class);
            when(response.getWriter()).thenReturn(writer);
            
            authenticationFilter.doFilter(request, response, filterChain);
            
            verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            verify(response).setContentType(MediaType.APPLICATION_JSON_VALUE);
            verify(writer).print("Authentication failed");
            verify(writer).flush();
            verify(writer).close();
            verify(filterChain, never()).doFilter(request, response);
        }
    }
}