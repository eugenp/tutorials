package com.baeldung.web.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.baeldung.persistence.model.Foo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

@Controller
@RequestMapping(value = "/foos")
public class FooController {

    // API

    // read - single
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Foo findById(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return new Foo(randomAlphabetic(6));
    }

    // read - multiple
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Foo> findAll() {
        return Lists.newArrayList(new Foo(randomAlphabetic(6)));
    }

    // write - just for test
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody final Foo foo) {
        return foo;
    }

    @PreAuthorize("hasPermission(#article, 'isEditor')")
    public void acceptArticle(Foo foo) {
        // logic here
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Foo foo) {
        // logic here
    }

    @PostAuthorize("returnObject.owner == authentication.name")
    public User getUser(Long id) {
        // get user logic here
        return null;
    }

    @PreFilter("filterObject.owner == authentication.name")
    public void updatePosts(List<Foo> posts) {
        // logic here
    }

    @PostFilter("filterObject.owner == authentication.name")
    public List<Foo> getPosts() {
        // logic here
        return null;
    }
}
