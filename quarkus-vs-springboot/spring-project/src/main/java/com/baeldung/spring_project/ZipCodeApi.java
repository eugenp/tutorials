package com.baeldung.spring_project;

import com.baeldung.spring_project.domain.ZIPRepo;
import com.baeldung.spring_project.domain.ZipCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Supplier;

@RestController
@RequestMapping(value = "/zipcode")
public class ZipCodeApi {

    private ZIPRepo zipRepo;

    public ZipCodeApi(ZIPRepo zipRepo) {
        this.zipRepo = zipRepo;
    }

    @GetMapping("/{zipcode}")
    public Mono<ZipCode> findById(@PathVariable String zipcode) {
        return zipRepo.findById(zipcode);
    }

    @GetMapping("/by_city")
    public Flux<ZipCode> postZipCode(@RequestParam String city) {
        return zipRepo.findByCity(city);
    }

    @Transactional
    @PostMapping
    public Mono<ZipCode> create(@RequestBody ZipCode zipCode) {
        return zipRepo.findById(zipCode.getZip()).switchIfEmpty(Mono.defer(createZipCode(zipCode)));
    }

    private Supplier<Mono<? extends ZipCode>> createZipCode(ZipCode zipCode) {
        return () -> {
            zipCode.setId(zipCode.getZip());
            return zipRepo.save(zipCode);
        };
    }
}
