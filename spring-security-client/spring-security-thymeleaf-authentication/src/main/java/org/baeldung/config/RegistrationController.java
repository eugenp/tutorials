package org.baeldung.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Manually authenticate a user using Spring Security / Spring Web MVC' (upon successful account registration)
 * (http://stackoverflow.com/questions/4664893/how-to-manually-set-an-authenticated-user-in-spring-security-springmvc)
 * 
 * @author jim clayson
 */
@Controller
@Profile("manual")
public class RegistrationController {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * For demo purposes this need only be a GET request method
     * 
     * @param request
     * @param response
     * @return The view. Page confirming either successful registration (and/or
     *         successful authentication) or failed registration.
     */
    @GetMapping("/register")
    public String registerAndAuthenticate(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("registerAndAuthenticate: attempt to register, application should manually authenticate.");

        // Mocked values. Potentially could come from an HTML registration form,
        // in which case this mapping would match on an HTTP POST, rather than a GET
        String username = "user";
        String password = "password";

        if (requestQualifiesForManualAuthentication()) {
            try {
                authenticate(username, password, request, response);
                logger.debug("registerAndAuthenticate: authentication completed.");
            } catch (BadCredentialsException bce) {
                logger.debug("Authentication failure: bad credentials");
                bce.printStackTrace();
                return "systemError"; // assume a low-level error, since the registration
                // form would have been successfully validated
            }
        }

        return "registrationSuccess";
    }

    private boolean requestQualifiesForManualAuthentication() {
        // Some processing to determine that the user requires a Spring Security-recognized,
        // application-directed login e.g. successful account registration.
        return true;
    }

    private void authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response) throws BadCredentialsException {
        logger.debug("attempting to authenticated, manually ... ");

        // create and populate the token
        AbstractAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        authToken.setDetails(new WebAuthenticationDetails(request));

        // This call returns an authentication object, which holds principle and user credentials
        Authentication authentication = this.authenticationManager.authenticate(authToken);

        // The security context holds the authentication object, and is stored
        // in thread local scope.
        SecurityContextHolder.getContext().setAuthentication(authentication);

        logger.debug("User should now be authenticated.");
    }

}