package com.baeldung.keycloak;

import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomUserAttrController {

    @GetMapping(path = "/users")
    public String getUserInfo(Model model) {

        final DefaultOidcUser user = (DefaultOidcUser) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal();

        String dob = "";
        String userId = "";

        OidcIdToken token = user.getIdToken();

        Map<String, Object> customClaims = token.getClaims();

        if (customClaims.containsKey("user_id")) {
            userId = String.valueOf(customClaims.get("user_id"));
        }

        if (customClaims.containsKey("DOB")) {
            dob = String.valueOf(customClaims.get("DOB"));
        }

        model.addAttribute("username", user.getName());
        model.addAttribute("userID", userId);
        model.addAttribute("dob", dob);
        return "userInfo";
    }

}
