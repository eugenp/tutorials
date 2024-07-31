package com.baeldung.loginextrafieldscustom;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        getDomain().ifPresent(d -> {
            model.addAttribute("domain", d);
        });
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(Model model) {
        getDomain().ifPresent(d -> {
            model.addAttribute("domain", d);
        });
        return "user/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    private Optional<String> getDomain() {
        Authentication auth = SecurityContextHolder.getContext()
            .getAuthentication(); 
        String domain = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            User user = (User) auth.getPrincipal();
            domain = user.getDomain();
        }
        return Optional.ofNullable(domain);
    }
}
