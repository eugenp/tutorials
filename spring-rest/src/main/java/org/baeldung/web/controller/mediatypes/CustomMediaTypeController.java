package org.baeldung.web.controller.mediatypes;

import org.baeldung.web.dto.BaeldungItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/", produces = "application/x-jsonp")
public class CustomMediaTypeController {

    @RequestMapping(method = RequestMethod.GET, value = "/public/api/endpoint", produces = "application/x-jsonp")
    public @ResponseBody BaeldungItem getItem() {
        return new BaeldungItem("itemId1");
    }
}
