package net.baeldung.ngrestboot.controllers;

import net.baeldung.ngrestboot.services.ExampleService;
import net.baeldung.ngrestboot.transfer.ResponseTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import net.baeldung.ngrestboot.transfer.LoginForm;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExampleController {

        @Autowired
        ExampleService exampleService;

        @PostMapping("/post")
        public boolean postController(@RequestBody LoginForm loginForm) {
               return exampleService.fakeAuthenticate(loginForm);
        }

        @PostMapping("/response")
        @ResponseBody
        public ResponseTransfer postResponseController(@RequestBody LoginForm loginForm) {
            return new ResponseTransfer("Thanks For Posting!!!");
        }
}