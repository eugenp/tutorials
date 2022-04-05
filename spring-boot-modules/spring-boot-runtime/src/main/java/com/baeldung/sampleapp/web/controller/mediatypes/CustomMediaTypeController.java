package com.baeldung.sampleapp.web.controller.mediatypes;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.sampleapp.web.dto.BaeldungItem;
import com.baeldung.sampleapp.web.dto.BaeldungItemV2;

@RestController
@RequestMapping(value = "/", produces = "application/vnd.baeldung.api.v1+json")
public class CustomMediaTypeController {

    @RequestMapping(method = RequestMethod.GET, value = "/public/api/items/{id}", produces = "application/vnd.baeldung.api.v1+json")
    public @ResponseBody BaeldungItem getItem(@PathVariable("id") String id) {
        return new BaeldungItem("itemId1");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/public/api/items/{id}", produces = "application/vnd.baeldung.api.v2+json")
    public @ResponseBody BaeldungItemV2 getItemSecondAPIVersion(@PathVariable("id") String id) {
        return new BaeldungItemV2("itemName");
    }
}
