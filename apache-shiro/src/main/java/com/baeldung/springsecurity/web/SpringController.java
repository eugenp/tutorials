package com.baeldung.springsecurity.web;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringController {

    @GetMapping("/")
    public String getIndex() {
        return "comparison/index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "comparison/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("error", "Invalid Credentials");
        return "comparison/login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest req) {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHomePage(HttpServletRequest req, Model model) {
        addUserAttributes(model);
        return "comparison/home";
    }

    @GetMapping("/admin")
    public String adminOnly(HttpServletRequest req, Model model) {
        addUserAttributes(model);
        model.addAttribute("adminContent", "only admin can view this");
        return "comparison/home";
    }

    private void addUserAttributes(Model model) {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication();
        if (auth != null && !auth.getClass()
            .equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            model.addAttribute("username", user.getUsername());

            Collection<GrantedAuthority> authorities = user.getAuthorities();

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority()
                    .contains("USER")) {
                    model.addAttribute("role", "USER");
                    model.addAttribute("permission", "READ");
                } else if (authority.getAuthority()
                    .contains("ADMIN")) {
                    model.addAttribute("role", "ADMIN");
                    model.addAttribute("permission", "READ WRITE");
                }
            }
        }
    }

}
