package org.baeldung.web.controller;


import org.baeldung.repository.HeavyResourceRepository;
import org.baeldung.web.dto.HeavyResource;
import org.baeldung.web.dto.HeavyResourceAddressOnly;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class HeavyResourceController {

    private HeavyResourceRepository heavyResourceRepository = new HeavyResourceRepository();

    @RequestMapping(value = "/heavy", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveResource(@RequestBody HeavyResource heavyResource) {
        heavyResourceRepository.save(heavyResource);
        return ResponseEntity.ok("resource saved");
    }

    @RequestMapping(value = "/heavy", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateName(@RequestBody HeavyResourceAddressOnly partialUpdate) {
        heavyResourceRepository.save(partialUpdate);
        return ResponseEntity.ok("resource address updated");
    }

    @RequestMapping(value = "/heavy/{id}", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> partialUpdateGeneric(@RequestBody Map<String, Object> updates,
                                                  @PathVariable("id") String id) {
        heavyResourceRepository.save(updates, id);
        return ResponseEntity.ok("resource updated");
    }

}
