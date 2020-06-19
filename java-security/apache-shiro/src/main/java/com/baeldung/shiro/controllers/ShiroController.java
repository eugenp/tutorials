package com.baeldung.shiro.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.baeldung.shiro.models.UserCredentials;

@Controller
public class ShiroController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(HttpServletRequest req, UserCredentials credentials, RedirectAttributes attr) {

        Subject subject = SecurityUtils.getSubject();

        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken(credentials.getUsername(), credentials.getPassword());
            try {
                subject.login(token);
            } catch (AuthenticationException ae) {
                ae.printStackTrace();
                attr.addFlashAttribute("error", "Invalid Credentials");
                return "redirect:/login";
            }
        }
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String getMeHome(Model model) {

        addUserAttributes(model);

        return "home";
    }

    @GetMapping("/admin")
    public String adminOnly(Model model) {
        addUserAttributes(model);

        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.hasRole("ADMIN")) {
            model.addAttribute("adminContent", "only admin can view this");
        }
        return "home";
    }

    @PostMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

    private void addUserAttributes(Model model) {
        Subject currentUser = SecurityUtils.getSubject();
        String permission = "";

        if (currentUser.hasRole("ADMIN")) {
            model.addAttribute("role", "ADMIN");
        } else if (currentUser.hasRole("USER")) {
            model.addAttribute("role", "USER");
        }

        if (currentUser.isPermitted("READ")) {
            permission = permission + " READ";
        }

        if (currentUser.isPermitted("WRITE")) {
            permission = permission + " WRITE";
        }
        model.addAttribute("username", currentUser.getPrincipal());
        model.addAttribute("permission", permission);
    }

}
