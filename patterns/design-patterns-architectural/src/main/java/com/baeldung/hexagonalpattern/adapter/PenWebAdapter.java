package com.baeldung.hexagonalpattern.adapter;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baeldung.hexagonalpattern.port.PenDetailInterface;

@RestController
public class PenWebAdapter {

    @Autowired
    private PenDetailInterface penDetailInterface;

    @GetMapping("/penTypes")
    public ResponseEntity<List<String>> getPenTypes() {
        return new ResponseEntity<List<String>>(penDetailInterface.getPenTypeDetails(), HttpStatus.OK);
    }
}
