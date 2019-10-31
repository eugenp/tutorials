package com.baeldung.restdocs;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ResourceSupport index() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(CRUDController.class).withRel("crud"));
        return index;
    }

}