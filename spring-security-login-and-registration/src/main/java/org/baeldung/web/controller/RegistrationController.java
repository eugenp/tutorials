package org.baeldung.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import javax.validation.Valid;

import org.baeldung.persistence.model.PasswordResetToken;
import org.baeldung.persistence.model.User;
import org.baeldung.persistence.model.VerificationToken;
import org.baeldung.persistence.service.IUserService;
import org.baeldung.persistence.service.UserDto;
import org.baeldung.registration.OnRegistrationCompleteEvent;
import org.baeldung.validation.EmailExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserDetailsService userDetailsService;

    public RegistrationController() {

    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        LOGGER.debug("Rendering registration page.");
        UserDto accountDto = new UserDto();
        model.addAttribute("user", accountDto);
        return "registration";
    }

    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        VerificationToken verificationToken = service.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        System.out.println(df.format(verificationToken.getExpiryDate()));
        System.out.println(df.format(cal.getTime()));

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        user.setEnabled(true);
        service.saveRegisteredUser(user);
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto accountDto, BindingResult result, WebRequest request, Errors errors) {
        LOGGER.debug("Registering user account with information: {}", accountDto);
        if (result.hasErrors()) {
            return new ModelAndView("registration", "user", accountDto);
        }

        User registered = createUserAccount(accountDto);
        if (registered == null) {
            result.rejectValue("email", "message.regError");
        }
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (Exception me) {
            return new ModelAndView("emailError", "user", accountDto);
        }
        return new ModelAndView("successRegister", "user", accountDto);
    }

    @RequestMapping(value = "/user/resendRegistrationToken", method = RequestMethod.GET)
    public String resendRegistrationToken(WebRequest request, Model model, @RequestParam("token") String token) {
        Locale locale = request.getLocale();
        VerificationToken newToken = service.updateVerificationToken(token);
        User user = service.getUser(newToken.getToken());
        try {
            String confirmationUrl = request.getContextPath() + "/regitrationConfirm.html?token=" + newToken.getToken();
            String message = messages.getMessage("message.resendToken", null, request.getLocale());
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject("Resend Registration Token");
            email.setText(message + " \r\n" + "http://localhost:8080" + confirmationUrl);
            System.out.println(email.getText());
            mailSender.send(email);
            System.out.println(email.getText());
        } catch (Exception e) {
            return "redirect:/emailError.html?lang=" + locale.getLanguage();
        }
        model.addAttribute("message", messages.getMessage("message.resendToken", null, locale));
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
    public String resetPassword(WebRequest request, Model model, @RequestParam("email") String userEmail) {

        User user = service.findUserByEmail(userEmail);
        if (user == null) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, request.getLocale()));
            return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
        }

        String token = UUID.randomUUID().toString();
        service.createPasswordResetTokenForUser(user, token);
        try {
            String url = request.getContextPath() + "/user/changePassword?id=" + user.getId() + "&token=" + token;
            String message = messages.getMessage("message.resetPassword", null, request.getLocale());
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(user.getEmail());
            email.setSubject("Reset Password");
            email.setText(message + " \r\n" + "http://localhost:8080" + url);
            System.out.println(email.getText());
            mailSender.send(email);
            System.out.println(email.getText());
        } catch (Exception e) {
            return "redirect:/emailError.html?lang=" + request.getLocale().getLanguage();
        }
        model.addAttribute("message", messages.getMessage("message.resetPassword", null, request.getLocale()));
        return "redirect:/login.html?lang=" + request.getLocale().getLanguage();
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
    public String changePassword(WebRequest request, Model model, @RequestParam("id") long id, @RequestParam("token") String token) {
        Locale locale = request.getLocale();

        PasswordResetToken passToken = service.getPasswordResetToken(token);
        User user = passToken.getUser();
        if (passToken == null || user.getId() != id) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            System.out.println(id);
            System.out.println(passToken);
            return "redirect:/login.html?lang=" + locale.getLanguage();
        }

        Calendar cal = Calendar.getInstance();
        if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            return "redirect:/login.html?lang=" + locale.getLanguage();
        }

        Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(user.getEmail()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/updatePassword.html?lang=" + locale.getLanguage();
    }

    @RequestMapping(value = "/user/savePassword", method = RequestMethod.POST)
    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String savePassword(WebRequest request, Model model, @RequestParam("password") String password) {
        Locale locale = request.getLocale();

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        service.changeUserPassword(user, password);

        return "redirect:/login.html?lang=" + locale;
    }

    private User createUserAccount(UserDto accountDto) {
        User registered = null;
        try {
            registered = service.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e) {
            return null;
        }
        return registered;
    }
}