package org.baeldung;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class HelloServletTest {
    @Autowired
    private MockHttpServletRequest request;
    @Autowired
    private MockHttpServletResponse response;

    @Before
    public void setUp() {
    }

    @Test
    public void whenRequested_thenForwardToCorrectUrl() throws ServletException, IOException {
        request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Dennis");
        HelloServlet servlet = new HelloServlet();
        MockServletContext servletContext = new MockServletContext();
        MockServletConfig servletConfig = new MockServletConfig(servletContext);
        servlet.init(servletConfig);
        response = new MockHttpServletResponse();
        servlet.doGet(request, response);
        assertEquals("/forwarded", response.getForwardedUrl());
        assertEquals(200, response.getStatus());
    }

}
