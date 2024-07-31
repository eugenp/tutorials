package com.baeldung.exclude_urls_filter.controller;

import com.baeldung.exclude_urls_filter.service.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FAQController {

    private final FAQService faqService;

    @Autowired
    public FAQController(FAQService faqService) {
        this.faqService = faqService;
    }

    @RequestMapping(value = "/faq/helpline", method = RequestMethod.GET)
    public ResponseEntity<String> getHelpLineNumber() {
        String helplineNumber = faqService.getHelpLineNumber();
        if (helplineNumber != null) {
            return new ResponseEntity<String>(helplineNumber, HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("Unavailable", HttpStatus.NOT_FOUND);
        }
    }


}
