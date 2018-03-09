package com.concretepage.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.concretepage.bean.Teacher;
@Controller
@RequestMapping("/hello")
public class HelloController {
	@Autowired
	private Teacher teacher;
	@RequestMapping
    public ModelAndView hello(ModelAndView  modelAndView) {
	  modelAndView.setViewName("hello");
	  modelAndView.addObject("city", teacher.getTeachAddress().getCity());
	  return modelAndView;
    }
} 