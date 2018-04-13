package com.baeldung.servlets;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class UnwelcomeServletTest {
    @Test
    public void whenRequested_thenRedirectedToCorrectUrl() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/welcome");
        request.addParameter("name", "Dennis");
        UnwelcomeServlet servlet = new UnwelcomeServlet();
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        servlet.doGet(request, response);
        
        assertEquals("/redirected", response.getRedirectedUrl());
        assertEquals(302, response.getStatus());
    }
}
