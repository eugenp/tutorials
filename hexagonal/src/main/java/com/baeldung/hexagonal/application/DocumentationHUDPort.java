package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Documentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DocumentationHUDPort {
        @PostMapping("create")
        public void create(@RequestBody String request);

        @GetMapping("view/{id}")
        public Documentation view(@PathVariable Long id);
}
