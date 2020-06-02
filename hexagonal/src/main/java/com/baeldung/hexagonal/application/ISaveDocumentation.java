package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.domain.Documentation;

public interface ISaveDocumentation {
        void save(Documentation documentation);
}
