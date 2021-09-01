package com.baeldung.hexagonalpattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import com.baeldung.hexagonalpattern.adapter.PenDataAPIImpl;
import com.baeldung.hexagonalpattern.adapter.PenWebAdapter;
import com.baeldung.hexagonalpattern.service.PenDetailService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.baeldung.hexagonalpattern.adapter", "com.baeldung.hexagonalpattern.port" })
@Import({ PenDetailService.class, PenWebAdapter.class, PenDataAPIImpl.class })
public class StationeryApplication {

    public static void main(String[] args) {
        SpringApplication.run(StationeryApplication.class, args);
    }

}
