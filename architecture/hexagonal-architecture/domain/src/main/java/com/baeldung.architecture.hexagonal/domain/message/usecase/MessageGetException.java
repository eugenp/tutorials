package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.domain.exception.AbstractDomaineNotFoundException;

class MessageGetException extends AbstractDomaineNotFoundException {
    @Override
    public String getDomaineName() {
        return "Message";
    }
}
