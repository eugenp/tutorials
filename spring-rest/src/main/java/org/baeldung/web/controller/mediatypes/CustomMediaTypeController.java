package org.baeldung.web.controller.mediatypes;


import org.baeldung.web.dto.BaeldungItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/vnd.baeldung.api.v1+json",
        consumes = "application/vnd.baeldung.api.v1+json")
public class CustomMediaTypeController {

    @RequestMapping(value = "/public/api/endpoint", produces = "application/vnd.baeldung.api.v1+json",
            consumes = "application/vnd.baeldung.api.v1+json")
    public @ResponseBody ResponseEntity<BaeldungItem> getItem() {
        return new ResponseEntity<>(new BaeldungItem("itemId1"), HttpStatus.OK);
    }
}
