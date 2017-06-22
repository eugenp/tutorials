package com.baeldung.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baeldung.service.IPLSeasonService;

@Controller
public class IplApiController {
	@RequestMapping("/api")
	@ResponseBody
	public String index() {
		return "IPL API";
	}

	@RequestMapping("/api/venues")
	@ResponseBody
	public List<String> getVenues() {
		try {
			return seasonService.getVenues();
		} catch (Exception ex) {
			return null;
		}
	}

	@RequestMapping("/api/season-format")
	@ResponseBody
	public String getSeasonFormat() {
		try {
			return seasonService.getFormat();
		} catch (Exception ex) {
			return null;
		}
	}

	@Autowired
	IPLSeasonService seasonService;

}
