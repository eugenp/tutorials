package org.baeldung.web.controller;


import org.baeldung.repository.HeavyResourceRepository;
import org.baeldung.web.dto.HeavyResource;
import org.baeldung.web.dto.HeavyResourceAddressPartialUpdate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HeavyResourceController {

    private HeavyResourceRepository heavyResourceRepository = new HeavyResourceRepository();

    @RequestMapping(value = "/heavy", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveResource(@RequestBody HeavyResource heavyResource) {
        heavyResourceRepository.save(heavyResource);
        return ResponseEntity.ok("resource saved");
    }

    @RequestMapping(value = "/heavy", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateName(@RequestBody HeavyResourceAddressPartialUpdate partialUpdate) {
        heavyResourceRepository.save(partialUpdate);
        return ResponseEntity.ok("resource address updated");
    }

}
