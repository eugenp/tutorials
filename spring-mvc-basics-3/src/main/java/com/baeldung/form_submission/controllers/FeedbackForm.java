package com.baeldung.form_submission.controllers;

import com.baeldung.form_submission.model.Feedback;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class FeedbackForm {

    @GetMapping(path = "/feedback")
    public String getFeedbackForm(Model model) {
        Feedback feedback = new Feedback();
        model.addAttribute("feedback", feedback);
        return "feedback";
    }

    @PostMapping(
      path = "/web/feedback",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public String handleBrowserSubmissions(Feedback feedback) throws Exception {
        // Save feedback data
        return "redirect:/feedback/success";
    }

    @GetMapping("/feedback/success")
    public ResponseEntity<String> getSuccess() {
        return new ResponseEntity<String>("Thank you for submitting feedback.", HttpStatus.OK);
    }

    @PostMapping(
      path = "/feedback",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<String> handleNonBrowserSubmissions(@RequestParam MultiValueMap paramMap) throws Exception {
        // Save feedback data
        return new ResponseEntity<String>("Thank you for submitting feedback", HttpStatus.OK);
    }
}