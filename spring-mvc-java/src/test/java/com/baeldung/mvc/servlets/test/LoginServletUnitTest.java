package com.baeldung.mvc.servlets.test;

import static org.junit.Assert.assertEquals;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockRequestDispatcher;

import com.baeldung.mvc.beans.User;
import com.baeldung.mvc.servlets.LoginServlet;

public class LoginServletUnitTest extends Mockito {

    @Mock
    MockHttpServletRequest request;

    @Mock
    MockHttpServletResponse response;

    @Mock
    MockRequestDispatcher rd;

    @Mock
    HttpSession session;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenLoginPageRequested_thenLoginPage() throws Exception {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(rd);
        new LoginServlet().doGet(request, response);
        verify(rd).forward(request, response);
    }

    @Test
    public void whenValidCredentials_thenWelcomePage() throws Exception {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("userid")).thenReturn("baeldung");
        when(request.getParameter("password")).thenReturn("baeldung");
        when(request.getRequestDispatcher("welcome.jsp")).thenReturn(rd);
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);

        new LoginServlet().doPost(request, response);

        // Verify that rd.forward method was called to redirect
        // to welcome.jsp
        verify(rd).forward(request, response);

        // Verify that loginUser attribute contains the userId that's
        // set in Loginservlet.java
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(request).setAttribute(anyString(), argument.capture());
        assertEquals("baeldung", argument.getValue()
            .getUserId());
    }

    @Test
    public void whenInvalidCredentials_thenErrorPage() throws Exception {

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);

        when(request.getParameter("userid")).thenReturn("invalid-id");
        when(request.getParameter("password")).thenReturn("invalid-password");
        when(request.getRequestDispatcher("error.jsp")).thenReturn(rd);
        when(request.getSession()).thenReturn(session);
        when(response.getWriter()).thenReturn(writer);

        new LoginServlet().doPost(request, response);

        // Verify that rd.forward method was called to redirect to
        // error.jsp
        verify(rd).forward(request, response);
    }

}
