package com.baeldung.restdocs;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/crud")
public class CRUDController {

    @GetMapping
    public List<CrudInput> read(@RequestBody @Valid CrudInput crudInput) {
        List<CrudInput> returnList = new ArrayList<>();
        returnList.add(crudInput);
        return returnList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public HttpHeaders save(@RequestBody @Valid CrudInput crudInput) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(linkTo(CRUDController.class).slash(crudInput.getId()).toUri());
        return httpHeaders;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    HttpHeaders delete(@PathVariable("id") long id) {
        return new HttpHeaders();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void put(@PathVariable("id") long id, @RequestBody CrudInput crudInput) {

    }

    @PatchMapping("/{id}")
    public List<CrudInput> patch(@PathVariable("id") long id, @RequestBody CrudInput crudInput) {
        List<CrudInput> returnList = new ArrayList<CrudInput>();
        crudInput.setId(id);
        returnList.add(crudInput);
        return returnList;
    }
}
