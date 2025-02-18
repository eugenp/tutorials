package com.baeldung.resttemplate.web.controller.redirect;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class RedirectParamController {

    @RequestMapping(value = "/forwardedWithParams", method = RequestMethod.GET)
    public RedirectView forwardedWithParams(final RedirectAttributes redirectAttributes, HttpServletRequest request) {

        redirectAttributes.addAttribute("param1", request.getAttribute("param1"));
        redirectAttributes.addAttribute("param2", request.getAttribute("param2"));

        redirectAttributes.addAttribute("attribute", "forwardedWithParams");
        return new RedirectView("redirectedUrl");

    }
}