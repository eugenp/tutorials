package com.baeldung.hexagonal.web;

import com.baeldung.hexagonal.domain.Feed;
import com.baeldung.hexagonal.domain.FeedReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FeedController {

    @Autowired
    private FeedReader feedReader;

    @GetMapping("/feed/{id}")
    ResponseEntity<Feed> getById(@PathVariable Long id) {
        Optional<Feed> feedOptional = feedReader.read(id);

        if (feedOptional.isPresent()) {
            return ResponseEntity.ok(feedOptional.get());
        }

        return ResponseEntity.status(404)
            .build();
    }
}
