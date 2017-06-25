package org.baeldung.security;

import javax.annotation.Resource;

import org.baeldung.user.service.MyUserService;
import org.baeldung.web.MyUserDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Resource
    MyUserService myUserService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerUserAccount(final MyUserDto accountDto, final Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        try {
            myUserService.registerNewUserAccount(accountDto);
            model.addAttribute("message", "Registration successful");
            return "index";
        } catch (final Exception exc) {
            model.addAttribute("message", "Registration failed");

            return "index";
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getHomepage(final Model model) {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("name", auth.getName());
        return "index";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegister() {
        return "register";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin() {
        return "login";
    }
}
