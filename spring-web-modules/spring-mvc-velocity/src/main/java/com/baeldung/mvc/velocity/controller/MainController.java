package com.baeldung.mvc.velocity.controller;

import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.ITutorialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private ITutorialsService tutService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcomePage() {
        return "index";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String listTutorialsPage(Model model) {
        List<Tutorial> list = tutService.listTutorials();
        model.addAttribute("tutorials", list);
        return "list";
    }

    public ITutorialsService getTutService() {
        return tutService;
    }

    public void setTutService(ITutorialsService tutService) {
        this.tutService = tutService;
    }

}