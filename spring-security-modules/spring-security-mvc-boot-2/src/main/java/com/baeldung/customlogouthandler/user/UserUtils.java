package com.baeldung.customlogouthandler.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtils {

    public static String getAuthenticatedUserLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername() : null;
    }

}
