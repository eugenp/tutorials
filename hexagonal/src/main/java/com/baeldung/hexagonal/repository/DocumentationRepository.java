package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.application.ILoadDocumentation;
import com.baeldung.hexagonal.application.ISaveDocumentation;
import org.springframework.stereotype.Repository;

@Repository public interface DocumentationRepository extends ILoadDocumentation, ISaveDocumentation {
}
