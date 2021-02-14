package com.baeldung.saml.controller;

import com.baeldung.saml.controller.model.DbAuthCredentials;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Handles standard form-based logins used to authenticate users against the database.
 * @author jcavazos
 */
@Controller
public class DbLoginController {

    private final AuthenticationManager authenticationManager;

    @Autowired
    public DbLoginController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/form-login")
    public String formLogin(@RequestParam(required = false) String username, Model model) {
        DbAuthCredentials credentials = new DbAuthCredentials();

        if (StringUtils.isNotBlank(username)) {
            credentials.setUsername(username);
        }

        model.addAttribute("credentials", credentials);
        return "form-login";
    }

    @PostMapping("/form-login")
    public String doLogin(@ModelAttribute DbAuthCredentials credentials,
                          RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(), credentials.getPassword()));

            if (authentication.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new Exception("Unauthenticated");
            }

            return "redirect:/landing";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Login Failed");
            return "redirect:/form-login?username="+credentials.getUsername();
        }
    }
}
