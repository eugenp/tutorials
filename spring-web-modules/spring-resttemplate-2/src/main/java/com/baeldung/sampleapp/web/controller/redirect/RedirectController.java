package com.baeldung.sampleapp.web.controller.redirect;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/")
public class RedirectController {

    @RequestMapping(value = "/redirectWithXMLConfig", method = RequestMethod.GET)
    public ModelAndView redirectWithUsingXMLConfig(final ModelMap model) {
        model.addAttribute("attribute", "redirectWithXMLConfig");
        return new ModelAndView("RedirectedUrl", model);
    }

    @RequestMapping(value = "/redirectWithRedirectPrefix", method = RequestMethod.GET)
    public ModelAndView redirectWithUsingRedirectPrefix(final ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectPrefix");
        return new ModelAndView("redirect:/redirectedUrl", model);
    }

    @RequestMapping(value = "/redirectWithRedirectAttributes", method = RequestMethod.GET)
    public RedirectView redirectWithRedirectAttributes(final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("flashAttribute", "redirectWithRedirectAttributes");
        redirectAttributes.addAttribute("attribute", "redirectWithRedirectAttributes");
        return new RedirectView("redirectedUrl");
    }

    @RequestMapping(value = "/redirectWithRedirectView", method = RequestMethod.GET)
    public RedirectView redirectWithUsingRedirectView(final ModelMap model) {
        model.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("redirectedUrl");
    }

    @RequestMapping(value = "/forwardWithForwardPrefix", method = RequestMethod.GET)
    public ModelAndView forwardWithUsingForwardPrefix(final ModelMap model) {
        model.addAttribute("attribute", "redirectWithForwardPrefix");
        return new ModelAndView("forward:/redirectedUrl", model);
    }

    @RequestMapping(value = "/redirectedUrl", method = RequestMethod.GET)
    public ModelAndView redirection(final ModelMap model, @ModelAttribute("flashAttribute") final Object flashAttribute) {
        model.addAttribute("redirectionAttribute", flashAttribute);
        return new ModelAndView("redirection", model);
    }

    @RequestMapping(value = "/redirectPostToPost", method = RequestMethod.POST)
    public ModelAndView redirectPostToPost(HttpServletRequest request) {
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/redirectedPostToPost");
    }

    @RequestMapping(value = "/redirectedPostToPost", method = RequestMethod.POST)
    public ModelAndView redirectedPostToPost() {
        return new ModelAndView("redirection");
    }
    
    @RequestMapping(value="/forwardWithParams", method = RequestMethod.GET)
    public ModelAndView forwardWithParams(HttpServletRequest request) {
        request.setAttribute("param1", "one");
        request.setAttribute("param2", "two");
        return new ModelAndView("forward:/forwardedWithParams");
    }
}