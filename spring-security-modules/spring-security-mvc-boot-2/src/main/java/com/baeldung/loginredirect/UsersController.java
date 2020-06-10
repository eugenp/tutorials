package com.baeldung.loginredirect;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class UsersController {

    @GetMapping("/userMainPage")
    public String getUserPage() {
        return "userMainPage";
    }

    @GetMapping("/loginUser")
    public String getUserLoginPage() {
        if (isAuthenticated()) {
            return "redirect:userMainPage";
        }
        return "loginUser";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
