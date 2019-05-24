package com.baeldung.hexagonalarchitecture.adapter.website.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.baeldung.hexagonalarchitecture.adapter.website.form.ApplicationsForm;
import com.baeldung.hexagonalarchitecture.core.domain.StudentApplication;
import com.baeldung.hexagonalarchitecture.core.service.StudentApplicationService;

@Controller("/")
public class StudentApplicationController {

	@Autowired
	private StudentApplicationService applicationService;

	@RequestMapping
	public ModelAndView getStudentApplications(ModelAndView mav, @ModelAttribute("form") ApplicationsForm form) {
		mav = new ModelAndView("index");

		if (form == null) {
			form = new ApplicationsForm();
		}

		List<StudentApplication> applications = new ArrayList<>();
		if (form.getId() == null) {
			applications.addAll(applicationService.findAll());
		} else {
			Optional<StudentApplication> app = applicationService.findById(form.getId());
			if (app.isPresent()) {
				applications.add(app.get());
			}
		}

		form.setApplications(applications);

		mav.addObject("form", form);
		return mav;
	}

	@PostMapping(params = { "remove" })
	public ModelAndView removeApplication(ModelAndView mav, @ModelAttribute(name = "form") ApplicationsForm form,
			BindingResult result, final HttpServletRequest request) {
		mav = new ModelAndView("index");
		Long id = Long.parseLong(request.getParameter("remove"));
		applicationService.deleteById(id);

		form.setApplications(applicationService.findAll());

		mav.addObject("form", form);
		return mav;
	}

	@PostMapping(params = { "transfer" })
	public ModelAndView transferApplication(ModelAndView mav, @ModelAttribute(name = "form") ApplicationsForm form,
			BindingResult result, final HttpServletRequest request) {
		mav = new ModelAndView("index");
		Long id = Long.parseLong(request.getParameter("transfer"));
		Optional<StudentApplication> optional = applicationService.findById(id);
		if (optional.isPresent()) {
			StudentApplication app = optional.get();
			applicationService.transferApplication(app);
		}

		form.setApplications(applicationService.findAll());

		mav.addObject("form", form);
		return mav;
	}

	@PostMapping(params = { "apply" })
	public ModelAndView addApplication(ModelAndView mav, @ModelAttribute(name = "form") ApplicationsForm form,
			BindingResult result, final HttpServletRequest request) {
		mav = new ModelAndView("index");
		StudentApplication application = new StudentApplication(form.getMajor(), form.getName());
		applicationService.saveApplication(application);

		form.setApplications(applicationService.findAll());

		mav.addObject("form", form);
		return mav;
	}
}