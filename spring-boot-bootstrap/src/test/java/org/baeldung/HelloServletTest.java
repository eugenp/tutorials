package org.baeldung;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class HelloServletTest {
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
