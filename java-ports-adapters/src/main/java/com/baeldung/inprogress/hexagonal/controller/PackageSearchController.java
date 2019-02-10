package com.baeldung.inprogress.hexagonal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.inprogress.hexagonal.core.backend.channel.IPackageSearchService;
import com.baeldung.inprogress.hexagonal.core.domain.SearchCriteria;

@RestController
public class PackageSearchController {

    @Autowired
    private IPackageSearchService defaultPackageSearchService;

    @RequestMapping(value = "/searchPackage", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity searchPackage(@RequestBody SearchCriteria searchCriteria) {

        try {
            return ResponseEntity.ok(defaultPackageSearchService.searchPackage(searchCriteria));

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
}
