package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ArtistController {

    @PostMapping("/artist")
    ResponseEntity<Void> addArtist(@RequestBody ArtistDto artistDto);

    @DeleteMapping("/artist")
    ResponseEntity<String> removeArtist(@RequestBody ArtistDto artistDto);

    @GetMapping("/artist/{artistId}")
    ResponseEntity<ArtistDto> getArtistById(@PathVariable Long artistId);

    @GetMapping("/artist")
    ResponseEntity<List<ArtistDto>> getArtists();

}
