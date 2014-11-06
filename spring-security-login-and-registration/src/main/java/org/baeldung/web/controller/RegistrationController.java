package org.baeldung.web.controller;

import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.persistence.service.UserDto;
import org.baeldung.persistence.service.IUserService;
import org.baeldung.validation.service.EmailExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private IUserService service;
	@Autowired
	// OCT 21
	private MessageSource messages;
	// OCT 21
	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	public RegistrationController(IUserService service) {
		this.service = service;
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.GET)
	public String showRegistrationForm(WebRequest request, Model model) {
		LOGGER.debug("Rendering registration page.");
		//Changed name to accountDto
		UserDto accountDto = new UserDto();
		model.addAttribute("user", accountDto);
		return "registration";
	}

	// OCT 21
	// FOR EMAIL ARTICLE
	@RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
	public String confirmRegistration(WebRequest request, Model model,
			@RequestParam("token") String token,
			@RequestParam("email") String email) {
		System.out.println(token);
		System.out.println(email);
		//NOV 5 get user by token
		User user = service.getRegisteredUser(email);
		
		if(user==null) {
			model.addAttribute("message",messages.getMessage("auth.message.invalidUser", null, request.getLocale()));
			//return "badUser";
			return "redirect:/badUser.html?lang="+request.getLocale().getLanguage();
		}
	
		VerificationToken verificationToken = user.getVerificationToken();
		if(!verificationToken.getToken().equals(token)) {
			model.addAttribute("message",messages.getMessage("auth.message.invalidToken", null, request.getLocale()));
			//return "badUser";
			return "redirect:/badUser.html?lang="+request.getLocale().getLanguage();
		}
		user.getVerificationToken().setVerified(true);
		service.verifyRegisteredUser(user);
		//return "login";
		return "redirect:/login.html?lang="+request.getLocale().getLanguage();
	}

	@RequestMapping(value = "/user/registration", method = RequestMethod.POST)
	public ModelAndView registerUserAccount(
			@ModelAttribute("user") @Valid UserDto accountDto,
			BindingResult result, WebRequest request, Errors errors) {
		//OCT 21
		LOGGER.debug("Registering user account with information: {}", accountDto);
		User registered = new User();
		//OCT 21
		String token = UUID.randomUUID().toString();
	    accountDto.setToken(token);
	    String appUrl = request.getContextPath();
		if (!result.hasErrors())
			registered = createUserAccount(accountDto, result);
		if (registered == null) {
			result.rejectValue("email", "message.regError");
		}
		if (result.hasErrors()) {
			return new ModelAndView("registration", "user", accountDto);
		} else {
			//OCT 21
			//FOR ARTICLE 2
	        //System.out.println("Will be Sending mail"); 
	        sendConfirmMail(accountDto.getEmail(), request.getLocale(), accountDto.getToken(), appUrl);
			return new ModelAndView("successRegister", "user", accountDto);
		}
	}

	private User createUserAccount(UserDto accountDto, BindingResult result) {
		User registered = null;
		try {
			registered = service.registerNewUserAccount(accountDto);
			
		} catch (EmailExistsException e) {
			return null;
		}
		return registered;
	}
	
	//OCT 21
	//FOR ARTICLE 2
    private void sendConfirmMail(String address, Locale locale, String token, String appUrl){
        String recipientAddress = address;
        String subject = "Registration Confirmation";
        String confirmationUrl = appUrl + "/regitrationConfirm.html?token="+token+"&email="+address;
        String message = messages.getMessage("message.regSucc", null, locale);       
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message +" \r\n"+ "http://localhost:8080"+confirmationUrl);
        mailSender.send(email);
    }
	

}
