package com.baeldung.session;

import mockit.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;

public class RetrieveSessionServletUnitTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Mocked
    HttpSession session;

    @Test
    void givenUserInSession_whenGetAttribute_thenUserIsReturned() throws Exception {
        User mockUser = new User("john_doe", "john@example.com");

        new Expectations() {{
            request.getSession(false); result = session;
            session.getAttribute("loggedInUser"); result = mockUser;
        }};

        new RetrieveSessionServlet().doGet(request, response);

        new Verifications() {{
            session.getAttribute("loggedInUser");
            times = 1;
        }};
    }

    @Test
    void givenNoSession_whenGetAttribute_thenWarnLogged() throws Exception {

        new Expectations() {{
            request.getSession(false); result = null;
        }};

        new RetrieveSessionServlet().doGet(request, response);
    }
}
