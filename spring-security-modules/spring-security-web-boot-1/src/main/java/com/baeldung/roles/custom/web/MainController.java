package com.baeldung.roles.custom.web;

import com.baeldung.roles.custom.persistence.dao.OrganizationRepository;
import com.baeldung.roles.custom.persistence.model.Foo;
import com.baeldung.roles.custom.persistence.model.Organization;
import com.baeldung.roles.custom.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class MainController {

    @Autowired
    private OrganizationRepository organizationRepository;

    // @PostAuthorize("hasPermission(returnObject, 'read')")
    @PreAuthorize("hasPermission(#id, 'Foo', 'read')")
    @GetMapping("/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable final long id) {
        return new Foo("Sample");
    }

    @PreAuthorize("hasPermission(#foo, 'write')")
    @PostMapping("/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody final Foo foo) {
        return foo;
    }

    @PreAuthorize("hasAuthority('FOO_READ_PRIVILEGE')")
    @GetMapping("/foos")
    @ResponseBody
    public Foo findFooByName(@RequestParam final String name) {
        return new Foo(name);
    }

    @PreAuthorize("isMember(#id)")
    @GetMapping("/organizations/{id}")
    @ResponseBody
    public Organization findOrgById(@PathVariable final long id) {
        return organizationRepository.findById(id)
            .orElse(null);
    }

    @PreAuthorize("hasPermission(#id, 'Foo', 'read')")
    @GetMapping("/user")
    @ResponseBody
    public MyUserPrincipal retrieveUserDetails(@AuthenticationPrincipal MyUserPrincipal principal) {
        return principal;
    }
}
