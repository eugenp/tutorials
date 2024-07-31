package com.baeldung.scopedvalues.scoped.inheriting;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServerUnitTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private final StringWriter writer = new StringWriter();

    private final Server server = new Server();

    @Test
    void givenMockedRequestWithAdminCredentials_whenServeMethodIsCalled_thenDataIsReturned() throws IOException {
        when(request.getParameter("user_name")).thenReturn("admin");
        when(request.getParameter("user_pw")).thenReturn("123456");
        when(request.getParameter("data_id")).thenReturn("1");
        when(response.getWriter()).thenReturn(new PrintWriter(writer));

        server.serve(request, response);

        assertThat(writer.toString()).isEqualTo("Data[id=1, title=Title 1, description=Description 1]\nExternal data");
    }

    @Test
    void givenMockedRequestWithUserCredentials_whenServeMethodIsCalled_thenNoDataIsReturned() throws IOException {
        when(request.getParameter("user_name")).thenReturn("user");
        when(request.getParameter("user_pw")).thenReturn("123456");

        server.serve(request, response);

        assertThat(writer.toString()).isEqualTo("");
    }

}
