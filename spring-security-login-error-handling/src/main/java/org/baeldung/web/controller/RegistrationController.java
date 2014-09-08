package org.baeldung.web.controller;

import javax.validation.Valid;

import org.baeldung.persistence.model.User;
import org.baeldung.persistence.service.EmailExistsException;
import org.baeldung.persistence.service.UserDto;
import org.baeldung.persistence.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private IUserService service;
    @Autowired
    private MessageSource messages;

    @Autowired
    public RegistrationController(IUserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        LOGGER.debug("Rendering registration page.");
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userAccountData, BindingResult result, WebRequest request, Errors errors) {

        User registered = new User();
        if (!result.hasErrors())
            registered = createUserAccount(userAccountData, result);
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", userAccountData);
        } else {
            // Will show the success registration page--ORIGINAL
            return new ModelAndView("successRegister", "user", userAccountData);

            // Will redirect to login view (not in url as login.html) and user model can be accessed
            // return new ModelAndView("login","user", userAccountData);
            
            
            // Will redirect to login html but no model object---we send a success param to the login form
            //return new ModelAndView("redirect:/login.html?success=true", "", null);
        }

    }

    private User createUserAccount(UserDto userAccountData, BindingResult result) {
        User registered = null;
        try {
            registered = service.registerNewUserAccount(userAccountData);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }

}
