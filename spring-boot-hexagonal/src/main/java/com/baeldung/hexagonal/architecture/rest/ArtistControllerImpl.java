package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.core.port.ArtistServicePort;
import com.baeldung.hexagonal.architecture.domain.data.ArtistDto;
import com.baeldung.hexagonal.architecture.domain.exception.ArtistNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ArtistControllerImpl implements ArtistController {

    private final ArtistServicePort artistServicePort;

    public ArtistControllerImpl(ArtistServicePort artistServicePort) {
        this.artistServicePort = artistServicePort;
    }

    @Override
    public ResponseEntity<ArtistDto> getArtistById(Long artistId) {
        try {
            return new ResponseEntity<>(artistServicePort.getArtistById(artistId), HttpStatus.OK);
        } catch (ArtistNotFoundException ex) {
            log.error("Error!", ex);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
