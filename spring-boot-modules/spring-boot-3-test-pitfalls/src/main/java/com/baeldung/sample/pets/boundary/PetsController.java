package com.baeldung.sample.pets.boundary;

import com.baeldung.sample.pets.domain.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetsController {

    private final PetService service;
    private final PetDtoMapper mapper;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<PetDto> readAll() {
        return service.getPets().stream()
          .map(mapper::map)
          .collect(Collectors.toList());
    }

}
