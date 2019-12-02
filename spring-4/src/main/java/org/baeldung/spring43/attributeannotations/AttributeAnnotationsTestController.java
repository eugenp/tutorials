package org.baeldung.spring43.attributeannotations;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class AttributeAnnotationsTestController {

    @GetMapping
    public String get(@SessionAttribute String login, @RequestAttribute String query) {
        return String.format("login = %s, query = %s", login, query);
    }

}