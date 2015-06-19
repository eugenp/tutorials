package org.baeldung.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.baeldung.persistence.dao.ActiveUserRepository;
import org.baeldung.persistence.dao.UserRepository;
import org.baeldung.web.util.ActiveUserSessionBindingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@Component("myAuthenticationSuccessHandler")
public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        handle(request, response, authentication);
        HttpSession session = request.getSession(false);
        if (session != null) {
            if (authentication != null) {
                Object principal = authentication.getPrincipal();
                if (principal != null && User.class.isAssignableFrom(principal.getClass())) {
                    User user = (User) principal;
                    WebApplicationContext webAppContext =  WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
                    
                    UserRepository userRepository = webAppContext.getBean(UserRepository.class);
                    ActiveUserRepository activeUserRepository = webAppContext.getBean(ActiveUserRepository.class);
                    
                    final ActiveUserSessionBindingListener sessionUser = new ActiveUserSessionBindingListener(user.getUsername(), request.getRemoteAddr(), userRepository);
                    sessionUser.setActiveUserRepository(activeUserRepository);
                    session.setAttribute(ActiveUserSessionBindingListener.SESSION_USER, sessionUser);
                }
            }
            session.setMaxInactiveInterval(30);
        }
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(Authentication authentication) {
        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("READ_PRIVILEGE")) {
                isUser = true;
            } else if (grantedAuthority.getAuthority().equals("WRITE_PRIVILEGE")) {
                isAdmin = true;
                isUser = false;
                break;
            }
        }
        if (isUser) {
            return "/homepage.html?user=" + authentication.getName();
        } else if (isAdmin) {
            return "/console.html";
        } else {
            throw new IllegalStateException();
        }
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }

}