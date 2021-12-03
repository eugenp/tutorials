package com.baeldung.architecture.hexagonal.domain.exception;

import lombok.Getter;

abstract class AbstractDomaineException extends RuntimeException {
    @Getter
    private String domaineName;
}
