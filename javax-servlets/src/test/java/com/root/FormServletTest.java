package com.root;

import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.Assert.*;

/**
 * Created by shubham on 27/11/16.
 */
public class FormServletTest extends Mockito {

    @Test
    public void testFormServlet() throws Exception {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("userName")).thenReturn("baeldung");
        when(request.getParameter("userPlanet")).thenReturn("Mars");

        PrintWriter writer = new PrintWriter(new StringWriter());
        when(response.getWriter()).thenReturn(writer);

        new FormServlet().doPost(request, response);

        assertTrue(request.getParameter("userName").contains("baeldung"));
    }
}
