package org.baeldung.ip.web;

import javax.servlet.http.HttpServletRequest;

import org.baeldung.custom.persistence.model.Foo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id, HttpServletRequest request) {
        return new Foo("Sample");
    }

}
