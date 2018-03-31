package com.baeldung.jstl.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class JSTLController {

    @RequestMapping(value = "/core_tags", method = RequestMethod.GET)
    public ModelAndView coreTags(final Model model) {
        ModelAndView mv = new ModelAndView("core_tags");
        return mv;
    }

    @RequestMapping(value = "/core_tags_redirect", method = RequestMethod.GET)
    public ModelAndView coreTagsRedirect(final Model model) {
        ModelAndView mv = new ModelAndView("core_tags_redirect");
        return mv;
    }


    @RequestMapping(value = "/formatting_tags", method = RequestMethod.GET)
    public ModelAndView formattingTags(final Model model) {
        ModelAndView mv = new ModelAndView("formatting_tags");
        return mv;
    }
}
