package com.baeldung.controllers;

import com.baeldung.models.UserCredentials;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ShiroSpringController {

    @GetMapping("/")
    public String index() {
       return "index";
    }

    @RequestMapping( value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(HttpServletRequest req, UserCredentials cred, RedirectAttributes attr) {

      if(req.getMethod().equals(RequestMethod.GET.toString())) {
        return "login";
      } else {
        Subject subject = SecurityUtils.getSubject();

        if(!subject.isAuthenticated()) {
          UsernamePasswordToken token = new UsernamePasswordToken(
            cred.getUsername(), cred.getPassword(), cred.isRememberMe());
          try {
            subject.login(token);
          } catch (AuthenticationException ae) {
              ae.printStackTrace();
              attr.addFlashAttribute("error", "Invalid Credentials");
              return "redirect:/login";
          }
        }

        return "redirect:/secure";
      }
    }


    @GetMapping("/secure")
    public String secure(ModelMap modelMap) {

        Subject currentUser = SecurityUtils.getSubject();
        String role = "", permission = "";

        if(currentUser.hasRole("admin")) {
            role = role  + "You are an Admin";
        }
        else if(currentUser.hasRole("editor")) {
            role = role + "You are an Editor";
        }
        else if(currentUser.hasRole("author")) {
            role = role + "You are an Author";
        }

        if(currentUser.isPermitted("articles:compose")) {
            permission = permission + "You can compose an article, ";
        } else {
            permission = permission + "You are not permitted to compose an article!, ";
        }

        if(currentUser.isPermitted("articles:save")) {
            permission = permission + "You can save articles, ";
        } else {
            permission = permission + "\nYou can not save articles, ";
        }

        if(currentUser.isPermitted("articles:publish")) {
            permission = permission  + "\nYou can publish articles";
        } else {
            permission = permission + "\nYou can not publish articles";
        }

        modelMap.addAttribute("username", currentUser.getPrincipal());
        modelMap.addAttribute("permission", permission);
        modelMap.addAttribute("role", role);

        return "secure";
    }


    @PostMapping("/logout")
    public String logout() {
       Subject subject = SecurityUtils.getSubject();
       subject.logout();
       return "redirect:/";
    }

}
