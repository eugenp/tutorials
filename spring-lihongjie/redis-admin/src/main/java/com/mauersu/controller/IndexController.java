package com.mauersu.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mauersu.util.Constant;
@Controller
public class IndexController implements Constant{

	@RequestMapping(value="/index", method=RequestMethod.GET)
	public Object index(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/redis";
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public Object home(HttpServletRequest request, HttpServletResponse response) {
		return "redirect:/redis";
	}
}
