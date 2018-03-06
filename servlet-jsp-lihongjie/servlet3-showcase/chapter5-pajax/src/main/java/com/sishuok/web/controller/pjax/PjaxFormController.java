/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.web.controller.pjax;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-26 下午10:26
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/form")
public class PjaxFormController {

    @RequestMapping()
    public String index() {
        return "form/index";
    }
    @RequestMapping(headers = "X-PJAX")
    public String index_fragment() {
        return "form/index_fragment";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showEditForm(
            Model model,
            @RequestHeader(value = "X-PJAX", defaultValue = "false") boolean isPjax) {

        if(isPjax) {
            return "form/edit_fragment";
        } else {
            return "form/edit";
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(
            @RequestHeader(value = "X-PJAX", defaultValue = "false") boolean isPjax,
            Model model,
            RedirectAttributes redirectAttributes) {

        if(!isPjax) {
            redirectAttributes.addFlashAttribute("message", "新增成功");
            return "redirect:/form";
        } else {
            model.addAttribute("message", "新增成功");
            return "form/index_fragment";
        }
    }

}
