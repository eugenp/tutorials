package com.baeldung.deletewrequestbody;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.deletewrequestbody.model.Body;

@RestController
@RequestMapping("/delete")
public class DeleteController {

    @DeleteMapping
    public Body delete(@RequestBody Body body) {
        return body;
    }
}
