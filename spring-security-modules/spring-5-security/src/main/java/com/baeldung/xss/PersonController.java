package com.baeldung.xss;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestController
@RequestMapping("/personService")
public class PersonController {

    @PostMapping(value = "/person")
    private ResponseEntity<String> savePerson(@RequestHeader Map<String, String> headers,
        @RequestParam String param, @RequestBody Person body) {
        ObjectNode response = JsonNodeFactory.instance.objectNode();
        headers.forEach((key, value) -> response.put(key, value));
        response.put("firstName", body.getFirstName());
        response.put("lastName", body.getLastName());
        response.put("age", body.getAge());
        response.put("param", param);
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }
}