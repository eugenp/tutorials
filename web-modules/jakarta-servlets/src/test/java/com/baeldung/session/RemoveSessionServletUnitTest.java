package com.baeldung.session;

import mockit.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;

public class RemoveSessionServletUnitTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Mocked
    HttpSession session;

    @Test
    void givenUserInSession_whenRemoveAttribute_thenSessionIsInvalidated() throws Exception {

        new Expectations() {{
            request.getSession(false); result = session;
            session.getId(); result = "TEST-SESSION-ID";
        }};

        new RemoveSessionServlet().doPost(request, response);

        new Verifications() {{
            session.removeAttribute("loggedInUser");
            times = 1;

            session.invalidate();
            times = 1;
        }};
    }

    @Test
    void givenNoSession_whenRemoveAttribute_thenWarnLogged() throws Exception {

        new Expectations() {{
            request.getSession(false); result = null;
        }};

        new RemoveSessionServlet().doPost(request, response);
    }
}
