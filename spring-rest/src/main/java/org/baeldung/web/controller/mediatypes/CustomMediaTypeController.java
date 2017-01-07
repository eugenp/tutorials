package org.baeldung.web.controller.mediatypes;

import org.baeldung.web.dto.BaeldungItem;
import org.baeldung.web.dto.BaeldungItemV2;
import org.springframework.web.bind.annotation.*;

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
