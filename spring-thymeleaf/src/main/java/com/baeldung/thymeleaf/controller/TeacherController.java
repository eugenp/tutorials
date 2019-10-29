package com.baeldung.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.thymeleaf.utils.TeacherUtils;

@Controller
public class TeacherController {

	@RequestMapping(value = "/listTeachers", method = RequestMethod.GET)
	public String getInfo(Model model) {
		model.addAttribute("teachers", TeacherUtils.buildTeachers());

		return "listTeachers.html";
	}
}
