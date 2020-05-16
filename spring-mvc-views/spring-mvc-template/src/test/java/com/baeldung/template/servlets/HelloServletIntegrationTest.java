package com.baeldung.template.servlets;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class HelloServletIntegrationTest {
    @Test
    public void whenRequested_thenForwardToCorrectUrl() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Dennis");
        MockHttpServletResponse response = new MockHttpServletResponse();
        HelloServlet servlet = new HelloServlet();

        servlet.doGet(request, response);

        assertEquals("/forwarded", response.getForwardedUrl());
        assertEquals(200, response.getStatus());
    }
}
