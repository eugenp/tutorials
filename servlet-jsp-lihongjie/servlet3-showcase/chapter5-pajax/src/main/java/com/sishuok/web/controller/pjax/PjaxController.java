/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.web.controller.pjax;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-7-26 下午10:26
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/pjax")
public class PjaxController {

    @RequestMapping()
    public String index() {
        return "pjax/index";
    }

    @RequestMapping(value = "/page1")
    public String page1(Model model) {
        return "pjax/page1";
    }

    @RequestMapping(value = "/page1", headers = "X-PJAX")
    public String page1_fragment(Model model) {
        return "pjax/page1_fragment";
    }

    @RequestMapping(value = "/page2")
    public String page2(Model model) {
        return "pjax/page2";
    }

    @RequestMapping(value = "/page2", headers = "X-PJAX")
    public String page2_fragment(Model model) {
        return "pjax/page2_fragment";
    }


    @RequestMapping(value = "/page3")
    public String page3(Model model) {
        return "pjax/page3";
    }

    @RequestMapping(value = "/page3", headers = "X-PJAX")
    public String page3_fragment(Model model) throws InterruptedException {
        Thread.sleep(2000L);
        return "pjax/page3_fragment";
    }

    @RequestMapping(value = "/page4")
    public String page4(Model model) {
        return "pjax/page4";
    }

}
