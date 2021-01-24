package com.baeldung.web.reactive.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebClientController {

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/resource")
    public Map<String, String> getResource() {
        Map<String, String> response = new HashMap<>();
        response.put("field", "value");
        return response;
    }

    @PostMapping("/resource")
    public String postResource(@RequestBody String bodyString) {
        return "processed-" + bodyString;
    }

    @PostMapping("/resource-foo")
    public String postResource(@RequestBody Foo bodyFoo) {
        return "processedFoo-" + bodyFoo.getName();
    }

    @PostMapping(value = "/resource-multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String handleFormUpload(@RequestPart("key1") String value1, @RequestPart("key2") String value2) {
        return "processed-" + value1 + "-" + value2;
    }
}
