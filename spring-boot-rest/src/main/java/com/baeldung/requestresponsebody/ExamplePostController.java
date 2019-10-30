package com.baeldung.requestresponsebody;

import com.baeldung.services.ExampleService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class ExamplePostController {

    private static Logger log = LoggerFactory.getLogger(ExamplePostController.class);

    @Autowired ExampleService exampleService;

    @PostMapping("/request")
    public ResponseEntity postController(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        exampleService.fakeAuthenticate(loginForm);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/response")
    @ResponseBody
    public ResponseTransfer postResponseController(@RequestBody LoginForm loginForm) {
        log.debug("POST received - serializing LoginForm: " + loginForm.getPassword() + " " + loginForm.getUsername());
        return new ResponseTransfer("Thanks For Posting!!!");
    }
}