package org.baeldung.web;

import org.baeldung.persistence.dao.OrganizationRepository;
import org.baeldung.persistence.model.Foo;
import org.baeldung.persistence.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MainController {

    @Autowired
    private OrganizationRepository organizationRepository;

    // @PostAuthorize("hasPermission(returnObject, 'read')")
    @PreAuthorize("hasPermission(#id, 'Foo', 'read')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
        return new Foo("Sample");
    }

    @PreAuthorize("hasPermission(#foo, 'write')")
    @RequestMapping(method = RequestMethod.POST, value = "/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody final Foo foo) {
        return foo;
    }

    //

    @PreAuthorize("hasAuthority('FOO_READ_PRIVILEGE')")
    @RequestMapping(method = RequestMethod.GET, value = "/foos")
    @ResponseBody
    public Foo findFooByName(@RequestParam final String name) {
        return new Foo(name);
    }

    //

    @PreAuthorize("isMember(#id)")
    @RequestMapping(method = RequestMethod.GET, value = "/organizations/{id}")
    @ResponseBody
    public Organization findOrgById(@PathVariable final long id) {
        return organizationRepository.findOne(id);
    }

}
