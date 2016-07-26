package com.baeldung.mvc.velocity.controller;

import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.ITutorialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller @RequestMapping("/") public class MainController {

    private final ITutorialsService tutService;

    @Autowired public MainController(ITutorialsService tutService) {
        this.tutService = tutService;
    }

    @RequestMapping(method = RequestMethod.GET) public String listTutorialsPage(Model model) {
        List<Tutorial> list = tutService.listTutorials();
        model.addAttribute("tutorials", list);
        return "index";
    }

    public ITutorialsService getTutService() {
        return tutService;
    }
}