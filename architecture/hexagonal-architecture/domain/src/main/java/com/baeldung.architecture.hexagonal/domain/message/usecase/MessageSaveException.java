package com.baeldung.architecture.hexagonal.domain.message.usecase;

import com.baeldung.architecture.hexagonal.domain.exception.AbstractDomaineSaveException;

class MessageSaveException extends AbstractDomaineSaveException {
    @Override
    public String getDomaineName() {
        return "Message";
    }
}
