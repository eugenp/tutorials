package com.baeldung.spring.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baeldung.spring.domain.MailObject;
import com.baeldung.spring.mail.EmailService;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    public EmailService emailService;

    @Value("${attachment.invoice}")
    private String attachmentPath;

    private static final Map<String, Map<String, String>> labels;

    static {
        labels = new HashMap<>();

        //Simple email
        Map<String, String> props = new HashMap<>();
        props.put("headerText", "Send Simple Email");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "");
        labels.put("send", props);

        //Email with template
        props = new HashMap<>();
        props.put("headerText", "Send Email Using Text Template");
        props.put("messageLabel", "Template Parameter");
        props.put("additionalInfo",
                "The parameter value will be added to the following message template:<br>" +
                        "<b>This is the test email template for your email:<br>'Template Parameter'</b>"
        );
        labels.put("sendTemplate", props);

        //Email with attachment
        props = new HashMap<>();
        props.put("headerText", "Send Email With Attachment");
        props.put("messageLabel", "Message");
        props.put("additionalInfo", "To make sure that you send an attachment with this email, change the value for the 'attachment.invoice' in the application.properties file to the path to the attachment.");
        labels.put("sendAttachment", props);

    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String showEmailsPage() {
        return "emails";
    }

    @RequestMapping(value = {"/send", "/sendTemplate", "/sendAttachment"}, method = RequestMethod.GET)
    public String createMail(Model model,
                             HttpServletRequest request) {
        String action = request.getRequestURL().substring(
                request.getRequestURL().lastIndexOf("/") + 1
        );
        Map<String, String> props = labels.get(action);
        Set<String> keys = props.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            model.addAttribute(key, props.get(key));
        }

        model.addAttribute("mailObject", new MailObject());
        return "mail/send";
    }


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public String createMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendSimpleMessage(mailObject.getTo(),
                mailObject.getSubject(), mailObject.getText());

        return "emails";
    }

    @RequestMapping(value = "/sendTemplate", method = RequestMethod.POST)
    public String createMailWithTemplate(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendSimpleMessageUsingTemplate(mailObject.getTo(),
                mailObject.getSubject(),
                mailObject.getText());

        return "redirect:/mail";
    }

    @RequestMapping(value = "/sendAttachment", method = RequestMethod.POST)
    public String createMailWithAttachment(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        emailService.sendMessageWithAttachment(
                mailObject.getTo(),
                mailObject.getSubject(),
                mailObject.getText(),
                attachmentPath
        );

        return "redirect:/mail";
    }
    

    @RequestMapping(value = {"/sendHtml"}, method = RequestMethod.GET)
    public String getHtmlMailView(Model model,
                             HttpServletRequest request) {

        Map<String, String> templateEngines = new HashMap<>();
        templateEngines.put("Thymeleaf", "Thymeleaf");
        templateEngines.put("Freemarker", "Freemarker");
        model.addAttribute("mailObject", new MailObject());
        model.addAttribute("templateEngines", templateEngines);
        return "mail/sendHtml";
    }

    @RequestMapping(value = "/sendHtml", method = RequestMethod.POST)
    public String createHtmlMail(Model model,
                             @ModelAttribute("mailObject") @Valid MailObject mailObject,
                             Errors errors) throws IOException, MessagingException, TemplateException {
        if (errors.hasErrors()) {
            return "mail/send";
        }
        
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("recipientName", mailObject.getRecipientName());
        templateModel.put("text", mailObject.getText());
        templateModel.put("senderName", mailObject.getSenderName());
        
        if (mailObject.getTemplateEngine().equalsIgnoreCase("thymeleaf")) {
            emailService.sendMessageUsingThymeleafTemplate(
                    mailObject.getTo(),
                    mailObject.getSubject(),
                    templateModel);
        } else {
            emailService.sendMessageUsingFreemarkerTemplate(
                mailObject.getTo(),
                mailObject.getSubject(),
                templateModel);
        }

        return "redirect:/mail";
    }
}
