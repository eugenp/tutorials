package org.baeldung;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class WelcomeServletTest {
    @Autowired
    private MockHttpServletRequest request;
    @Autowired
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
    }

    @Test
    public void whenRequested_thenRedirectedToCorrrectUrl() throws ServletException, IOException {
        request = new MockHttpServletRequest("GET", "/welcome");
        request.addParameter("name", "Dennis");
        WelcomeServlet servlet = new WelcomeServlet();
        MockServletContext servletContext = new MockServletContext();
        MockServletConfig servletConfig = new MockServletConfig(servletContext);
        servlet.init(servletConfig);
        response = new MockHttpServletResponse();
        servlet.doGet(request, response);
        assertEquals("/redirected", response.getRedirectedUrl());
        assertEquals(302, response.getStatus());
    }
}
