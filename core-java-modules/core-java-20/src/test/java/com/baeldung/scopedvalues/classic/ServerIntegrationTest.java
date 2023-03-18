package com.baeldung.scopedvalues.classic;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ServerIntegrationTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final StringWriter writer = new StringWriter();

    private final Server server = new Server();

    @Test
    void givenHttpServletRequest_whenMockedWithMockito_thenReturnsParameterValues() throws IOException {
        when(request.getParameter("user_id")).thenReturn("admin");
        when(request.getParameter("user_pw")).thenReturn("123456");
        when(request.getParameter("data_id")).thenReturn("1");
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        server.serve(request, response);

        assertThat(writer.toString()).isEqualTo("Full Name: Mockito Test");
    }

}
