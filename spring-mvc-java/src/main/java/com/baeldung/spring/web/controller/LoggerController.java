package com.baeldung.spring.web.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.baeldung.spring.web.config.LogAccessService;
import com.baeldung.spring.web.config.LogConfiguration;
@Controller
public class LoggerController {
	@Autowired
	private LogConfiguration environment;

	@Autowired
	private LogAccessService logService;

	@Autowired
	private WebApplicationContext servletContext;

	@RequestMapping("/checkin/{idCode}")
	@ResponseBody
	public String checkIn(@PathVariable final int idCode) {
		System.out.println("checkIn called");
		final String companyName = environment.getCompanyName();
		final String servletName = servletContext.getDisplayName();
		// final String shortName = servletName.substring(37,
		// servletName.lastIndexOf('-'));
		final String ret = logService.logAccess(servletName + "@" + companyName, idCode);
		return ret;
	}
}