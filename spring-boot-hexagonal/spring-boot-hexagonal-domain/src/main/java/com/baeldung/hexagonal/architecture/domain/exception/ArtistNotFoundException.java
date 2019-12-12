package com.baeldung.hexagonal.architecture.domain.exception;

public class ArtistNotFoundException extends RuntimeException {

        public ArtistNotFoundException(Long id) {
                super("Artist with id %s not found!".formatted(id));
        }
}
