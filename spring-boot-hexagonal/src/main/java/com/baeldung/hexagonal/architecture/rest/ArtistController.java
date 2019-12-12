package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ArtistController {

    @GetMapping("/artist/{artistId}")
    ResponseEntity<ArtistDto> getArtistById(@PathVariable Long artistId);

}
