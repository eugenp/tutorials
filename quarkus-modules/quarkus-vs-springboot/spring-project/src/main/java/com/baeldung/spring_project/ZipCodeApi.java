package com.baeldung.spring_project;

import com.baeldung.spring_project.domain.ZIPRepo;
import com.baeldung.spring_project.domain.ZipCode;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.r2dbc.UncategorizedR2dbcException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
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
        return getById(zipcode);
    }

    @GetMapping("/by_city")
    public Flux<ZipCode> postZipCode(@RequestParam String city) {
        return zipRepo.findByCity(city);
    }

    @PostMapping
    public Mono<ZipCode> create(@RequestBody ZipCode zipCode) {
        return getById(zipCode.getZip())
                .switchIfEmpty(Mono.defer(createZipCode(zipCode)))
                .onErrorResume(this::isKeyDuplicated, this.recoverWith(zipCode));
    }

    private Mono<ZipCode> getById(String zipCode) {
        return zipRepo.findById(zipCode);
    }

    private boolean isKeyDuplicated(Throwable ex) {
        return ex instanceof DataIntegrityViolationException || ex instanceof UncategorizedR2dbcException;
    }

    private Function<? super Throwable, ? extends Mono<ZipCode>> recoverWith(ZipCode zipCode) {
        return throwable -> zipRepo.findById(zipCode.getZip());
    }

    private Supplier<Mono<? extends ZipCode>> createZipCode(ZipCode zipCode) {
        return () -> {
            zipCode.setId(zipCode.getZip());
            return zipRepo.save(zipCode);
        };
    }
}
