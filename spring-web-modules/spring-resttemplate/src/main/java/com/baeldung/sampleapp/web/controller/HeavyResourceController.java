package com.baeldung.sampleapp.web.controller;


import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.sampleapp.repository.HeavyResourceRepository;
import com.baeldung.sampleapp.web.dto.HeavyResource;
import com.baeldung.sampleapp.web.dto.HeavyResourceAddressOnly;

@RestController
public class HeavyResourceController {

    private HeavyResourceRepository heavyResourceRepository = new HeavyResourceRepository();

    @RequestMapping(value = "/heavyresource/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveResource(@RequestBody HeavyResource heavyResource, @PathVariable("id") String id) {
        heavyResourceRepository.save(heavyResource, id);
        return ResponseEntity.ok("resource saved");
    }

    @RequestMapping(value = "/heavyresource/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateName(@RequestBody HeavyResourceAddressOnly partialUpdate, @PathVariable("id") String id) {
        heavyResourceRepository.save(partialUpdate, id);
        return ResponseEntity.ok("resource address updated");
    }

    @RequestMapping(value = "/heavyresource2/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateGeneric(@RequestBody Map<String, Object> updates,
                                                  @PathVariable("id") String id) {
        heavyResourceRepository.save(updates, id);
        return ResponseEntity.ok("resource updated");
    }

}
