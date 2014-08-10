package org.baeldung.web.controller;

import java.util.Locale;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.EmailExistsException;
import org.baeldung.persistence.service.UserDto;
import org.baeldung.persistence.service.UserService;
import org.baeldung.persistence.service.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SessionAttributes("user")
public class RegistrationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);
    private UserService service;
    @Autowired
    private MessageSource messages;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserValidator validator;
    
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(this.validator);
    }
    @Autowired
    public RegistrationController(UserService service) {
        this.service = service;
    }
    
    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        LOGGER.debug("Rendering registration page.");
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }
  /*  @RequestMapping(value ="/user/registration", method = RequestMethod.POST)
    public String registerUserAccount( @ModelAttribute("user") UserDto userAccountData,
                                      BindingResult result,
                                      WebRequest request, Errors errors)  {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        if (result.hasErrors()) {
            LOGGER.debug("Validation errors found. Rendering form view.");
            return "registration";
        }
        LOGGER.debug("No validation errors found. Continuing registration process.");
        User registered = createUserAccount(userAccountData, result);
        if (registered == null) {        
            errors.rejectValue("lastError", "message.regError");
            return "registration";
        }
        LOGGER.debug("Registered user account with information: {}", registered);
        
        sendConfirmMail(userAccountData.getUsername(), request.getLocale());
        return "successRegister";
        //return "redirect:/";
    }*/
    @RequestMapping(value ="/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount( @ModelAttribute("user") UserDto userAccountData,
                                      BindingResult result,
                                      WebRequest request, Errors errors)  {
        LOGGER.debug("Registering user account with information: {}", userAccountData);
        validator.validate(userAccountData, result);
        User registered = createUserAccount(userAccountData, result);
        if (registered == null) {           
            result.rejectValue("lastError", "message.regError");
        }
        if (result.hasErrors()) {
            // show errors
            return new ModelAndView("registration", "user", userAccountData);
        } else {
           
            // success
            return new ModelAndView("successRegister", "user", userAccountData);
        }    
        
    }
    
    private User createUserAccount(UserDto userAccountData, BindingResult result) {
        LOGGER.debug("Creating user account with information: {}", userAccountData);
        User registered = null;
        try {
            registered = service.registerNewUserAccount(userAccountData);
        } catch (EmailExistsException e) {
            // TODO Auto-generated catch block
            return null;
        }    
        return registered;
    }   
    
    private void sendConfirmMail(String address, Locale locale){
        String recipientAddress = address;
        String subject = "Registration Confirmation";
        String message = messages.getMessage("message.regSucc", null, locale);    
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message);
        mailSender.send(email);
    }
}
