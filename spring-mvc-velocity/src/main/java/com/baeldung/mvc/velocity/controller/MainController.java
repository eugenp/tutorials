package com.baeldung.mvc.velocity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.mvc.velocity.domain.Tutorial;
import com.baeldung.mvc.velocity.service.TutorialsService;

@Controller
public class MainController {
 
   @Autowired
   private TutorialsService tutService;
 
   @RequestMapping(value = { "/" }, method = RequestMethod.GET)
   public String listTutorialsPage(Model model) {
       List<Tutorial> list = tutService.listTutorials();
       model.addAttribute("tutorials", list);
       return "index";
   }

public TutorialsService getTutService() {
	return tutService;
}

public void setTutService(TutorialsService tutService) {
	this.tutService = tutService;
}
   
   
}