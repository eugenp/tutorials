package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.Documentation;
import org.springframework.stereotype.Repository;

@Repository public interface DocumentationRepository {
        void create(String content);

        Documentation getDocumentation(Long id);
}
