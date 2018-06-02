package com.baeldung.javaee.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@ApplicationScoped
public class CustomAuthentication implements HttpAuthenticationMechanism {

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse,
                                                HttpMessageContext httpMessageContext) throws AuthenticationException {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        //Mocking UserDetail, but in real life, we can find it from a database.
        UserDetail userDetail = findByUserNameAndPassword(username, password);
        if (userDetail != null) {
            return httpMessageContext.notifyContainerAboutLogin(
                    new CustomPrincipal(userDetail),
                    new HashSet<>(userDetail.getRoles()));
        }
        return httpMessageContext.responseUnauthorized();
    }

    private UserDetail findByUserNameAndPassword(String username, String password) {
        UserDetail userDetail = new UserDetail("uid_10", username, password);
        userDetail.addRole("admin_role");
        return userDetail;
    }
}
