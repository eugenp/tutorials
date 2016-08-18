package com.baeldung.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Olga on 7/20/2016.
 */
@Controller
@RequestMapping("/mail")
public class MailController {
    /*@Autowired
    public EMailService mailService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String createMail(Model model) {
        model.addAttribute("mailObject", new MailObject());
        return "mail/send";
    }

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if(errors.hasErrors()) {
            return "mail/send";
        }

        SimpleMailMessage mailMessage = mailService.createSimpleMailMessage(mailObject);
        mailService.sendMail(mailMessage);

        return "redirect:/home";
    }*/
}
