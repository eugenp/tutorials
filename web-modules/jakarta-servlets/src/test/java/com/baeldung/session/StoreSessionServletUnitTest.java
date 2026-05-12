package com.baeldung.session;

import mockit.*;
import jakarta.servlet.http.*;
import org.junit.jupiter.api.Test;

public class StoreSessionServletUnitTest {

    @Mocked
    HttpServletRequest request;

    @Mocked
    HttpServletResponse response;

    @Mocked
    HttpSession session;

    @Test
    void givenUser_whenStoreInSession_thenAttributeIsSet() throws Exception {

        new Expectations() {{
            request.getSession(); result = session;
            session.getId(); result = "TEST-SESSION-ID";
        }};

        new StoreSessionServlet().doPost(request, response);

        new Verifications() {{
            session.setAttribute("loggedInUser", any);
            times = 1;
        }};
    }
}
