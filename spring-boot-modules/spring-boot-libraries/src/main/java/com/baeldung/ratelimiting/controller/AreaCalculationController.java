package com.baeldung.ratelimiting.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ratelimiting.dto.AreaV1;
import com.baeldung.ratelimiting.dto.RectangleDimensionsV1;
import com.baeldung.ratelimiting.dto.TriangleDimensionsV1;

@RestController
@RequestMapping(value = "/api/v1/area", consumes = MediaType.APPLICATION_JSON_VALUE)
class AreaCalculationController {

    @PostMapping(value = "/rectangle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AreaV1> rectangle(@RequestBody RectangleDimensionsV1 dimensions) {

        return ResponseEntity.ok(new AreaV1("rectangle", dimensions.getLength() * dimensions.getWidth()));
    }

    @PostMapping(value = "/triangle", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AreaV1> triangle(@RequestBody TriangleDimensionsV1 dimensions) {

        return ResponseEntity.ok(new AreaV1("triangle", 0.5d * dimensions.getHeight() * dimensions.getBase()));
    }
}
