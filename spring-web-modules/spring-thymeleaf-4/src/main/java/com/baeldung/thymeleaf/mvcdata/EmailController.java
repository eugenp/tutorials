package com.baeldung.thymeleaf.mvcdata;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.baeldung.thymeleaf.mvcdata.repository.EmailData;

@Controller
public class EmailController {
    private EmailData emailData = new EmailData();
    private ServletContext servletContext;

    public EmailController(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @GetMapping(value = "/email/modelattributes")
    public String emailModel(Model model) {
        model.addAttribute("emaildata", emailData);
        return "mvcdata/email-model-attributes";
    }

    @ModelAttribute("emailModelAttribute")
    EmailData emailModelAttribute() {
        return emailData;
    }

    @GetMapping(value = "/email/requestparameters")
    public String emailRequestParameters(
            @RequestParam(value = "emailsubject") String emailSubject,
            @RequestParam(value = "emailcontent") String emailContent,
            @RequestParam(value = "emailaddress") String emailAddress1,
            @RequestParam(value = "emailaddress") String emailAddress2,
            @RequestParam(value = "emaillocale") String emailLocale) {
        return "mvcdata/email-request-parameters";
    }

    @GetMapping("/email/beandata")
    public String emailBeanData() {
        return "mvcdata/email-bean-data";
    }
}
