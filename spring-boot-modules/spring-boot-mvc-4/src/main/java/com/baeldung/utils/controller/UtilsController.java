package com.baeldung.utils.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.util.WebUtils;

@Controller
public class UtilsController {

    @GetMapping("/utils")
    public String webUtils(Model model) {
        return "utils";
    }

    @PostMapping("/setParam")
    public String post(HttpServletRequest request, Model model) {
        String param = ServletRequestUtils.getStringParameter(request, "param", "DEFAULT");

        // Long param = ServletRequestUtils.getLongParameter(request, "param",1L);
        // boolean param = ServletRequestUtils.getBooleanParameter(request, "param", true);
        // double param = ServletRequestUtils.getDoubleParameter(request, "param", 1000);
        // float param = ServletRequestUtils.getFloatParameter(request, "param", (float) 1.00);
        // int param = ServletRequestUtils.getIntParameter(request, "param", 100);

        // try {
        // ServletRequestUtils.getRequiredStringParameter(request, "param");
        // } catch (ServletRequestBindingException e) {
        // e.printStackTrace();
        // }

        WebUtils.setSessionAttribute(request, "parameter", param);
        model.addAttribute("parameter", "You set: " + (String) WebUtils.getSessionAttribute(request, "parameter"));
        return "utils";
    }

    @GetMapping("/other")
    public String other(HttpServletRequest request, Model model) {
        String param = (String) WebUtils.getSessionAttribute(request, "parameter");
        model.addAttribute("parameter", param);
        return "other";
    }

}
